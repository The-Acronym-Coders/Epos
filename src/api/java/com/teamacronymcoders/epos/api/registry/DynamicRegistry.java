/*
 * MIT License
 *
 * Copyright (c) 2019-2021 Team Acronym Coders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.teamacronymcoders.epos.api.registry;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

// TODO: Finish, use FMLCommonSetupEvent to create registry
public class DynamicRegistry<V extends IDynamic<V, ?>, S extends ISerializer<V, S>>
        implements IDynamicRegistry<V, S>, Codec<V> {

    private static final Logger LOGGER = LogManager.getLogger("Dynamic Registries");
    public static final ResourceLocation MISSING_ENTRY = new ResourceLocation(Epos.ID, "missing");
    private final ResourceLocation name;
    private final Class<V> superType;
    private final Map<ResourceLocation, V> entries;
    private final V missingEntry;
    private final IForgeRegistry<S> serializationRegistry;
    private final Codec<V> entryCodec;
    private final Codec<Snapshot<V, S>> snapshotCodec;
    private boolean isFrozen;

    public DynamicRegistry(DynamicRegistryBuilder<V, S> builder) {
        this.name = builder.getName();
        this.superType = builder.getType();
        this.serializationRegistry = builder.getSerializer();
        this.entries = new HashMap<>();
        this.missingEntry = builder.getMissingEntry();
        this.missingEntry.setRegistryName(MISSING_ENTRY);
        this.entryCodec = EposCodecs.dynamicRegistryEntry(this.serializationRegistry);
        this.snapshotCodec = RecordCodecBuilder.create(instance -> {
            return instance.group(Codec.unboundedMap(ResourceLocation.CODEC, this.entryCodec).fieldOf("entries")
                    .forGetter(snap -> snap.entries)).apply(instance, Snapshot::new);
        });
        this.addMissingEntry();
        this.freeze();
    }

    private void addMissingEntry() {
        this.register(this.missingEntry);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return this.name;
    }

    @Override
    public Class<V> getSuperType() {
        return this.superType;
    }

    @Override
    public void register(V val) {
        if (this.isLocked())
            throw new IllegalStateException("The registry is currently locked: " + this.getRegistryName());

        ResourceLocation registryName = val.getRegistryName();
        if (registryName == null)
            throw new IllegalArgumentException("There is no registry name assigned with the registree " + val);
        else if (this.containsValue(val))
            throw new IllegalArgumentException("The registry object has already been registered within "
                    + this.getRegistryName() + ": " + val.getRegistryName());
        else if (this.containsKey(registryName)) {
            if (registryName == MISSING_ENTRY)
                throw new IllegalArgumentException(
                        "The missing entry cannot be overridden within " + this.getRegistryName());
            LOGGER.warn("{} is already associated with an entry within {}. Replacing {} -> {}.", registryName,
                    this.getRegistryName(), this.get(registryName), val);
        }
        this.entries.put(registryName, val);
    }

    public void registerDynamic(ResourceLocation entryName, JsonElement element) {
        this.parse(JsonOps.INSTANCE, element)
                .resultOrPartial(
                        str -> LOGGER.error("Something went wrong trying to deserialize {} into the {} registry: {}",
                                entryName, this.getRegistryName(), str))
                .ifPresent(obj -> {
                    obj.setRegistryName(entryName);
                    this.register(obj);
                });
    }

    public void freeze() {
        this.isFrozen = true;
    }

    public boolean isLocked() {
        return this.isFrozen;
    }

    public void unfreeze() {
        this.isFrozen = false;
    }

    public Snapshot<V, S> snapshot() {
        return new Snapshot<>(this);
    }

    public void writeRegistry(PacketBuffer buffer) {
        try {
            buffer.writeWithCodec(this.snapshotCodec, this.snapshot());
        } catch (IOException e) {
            LOGGER.error("An error has occurred while trying to write {} to a buffer: {}", this.getRegistryName(),
                    e.getMessage());
            throw new RuntimeException("An IO write exception has occurred within " + this.getRegistryName(), e);
        }
    }

    public void readRegistry(PacketBuffer buffer) {
        try {
            this.updateRegistry(buffer.readWithCodec(this.snapshotCodec));
        } catch (IOException e) {
            LOGGER.error("An error has occurred while trying to read {} to a buffer: {}", this.getRegistryName(),
                    e.getMessage());
            throw new RuntimeException("An IO read exception has occurred within " + this.getRegistryName(), e);
        }
    }

    public <T> DataResult<T> encodeRegistry(DynamicOps<T> ops) {
        return this.encodeRegistry(ops, ops.empty());
    }

    public <T> DataResult<T> encodeRegistry(DynamicOps<T> ops, T prefix) {
        return this.snapshotCodec.encode(this.snapshot(), ops, prefix);
    }

    @Override
    public <T> DataResult<T> encode(V input, DynamicOps<T> ops, T prefix) {
        return this.entryCodec.encode(input, ops, prefix);
    }

    public <T> void updateRegistry(DynamicOps<T> ops, T input) {
        this.updateRegistry(this.snapshotCodec.parse(ops, input).getOrThrow(true,
                str -> LOGGER.error("An error has occurred while trying to parse the registry snapshot for {}: {}",
                        this.getRegistryName(), str)));

    }

    private void updateRegistry(Snapshot<V, S> snapshot) {
        this.unfreeze();
        this.clear();
        snapshot.entries.forEach((loc, val) -> {
            val.setRegistryName(loc);
            this.register(val);
        });
        this.freeze();
    }

    @Override
    public <T> DataResult<Pair<V, T>> decode(DynamicOps<T> ops, T input) {
        return this.entryCodec.decode(ops, input);
    }

    @Override
    public V get(ResourceLocation loc) {
        return this.entries.get(loc);
    }

    @Override
    public void clear() {
        if (this.isLocked())
            throw new IllegalStateException("The registry is currently locked: " + this.getRegistryName());
        this.entries.clear();
        this.addMissingEntry();
    }

    @Override
    public boolean containsKey(ResourceLocation loc) {
        return this.entries.containsKey(loc);
    }

    @Override
    public boolean containsValue(V value) {
        return this.entries.containsValue(value);
    }

    @Override
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    @Override
    public void forEach(BiConsumer<ResourceLocation, V> consumer) {
        this.entries.forEach(consumer);
    }

    public static class Snapshot<V extends IDynamic<V, ?>, S extends ISerializer<V, S>> {

        private final Map<ResourceLocation, V> entries;

        public Snapshot(DynamicRegistry<V, S> registry) {
            this(registry.entries);
        }

        private Snapshot(Map<ResourceLocation, V> entries) {
            this.entries = entries;
        }
    }
}

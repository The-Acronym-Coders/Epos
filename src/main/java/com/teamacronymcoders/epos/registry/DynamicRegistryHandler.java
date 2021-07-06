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

package com.teamacronymcoders.epos.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.gson.JsonElement;
import com.teamacronymcoders.epos.api.registry.DynamicRegistry;
import com.teamacronymcoders.epos.api.registry.DynamicRegistryBuilder;
import com.teamacronymcoders.epos.api.registry.IDynamic;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import com.teamacronymcoders.epos.network.DynamicRegistryPacket;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: Finish
public class DynamicRegistryHandler {

    private static final Logger LOGGER = LogManager.getLogger("Dynamic Registry Handler");
    public static final DynamicRegistryHandler INSTANCE = new DynamicRegistryHandler();
    private final Map<ResourceLocation, DynamicRegistry<?, ?>> registries;
    private final BiMap<Class<? extends IDynamic<?, ?>>, ResourceLocation> superTypes;
    private final Set<ResourceLocation> persisted;
    private final Set<ResourceLocation> synced;
    private boolean needsSync;

    public DynamicRegistryHandler() {
        this.registries = new HashMap<>();
        this.superTypes = HashBiMap.create();
        this.persisted = new HashSet<>();
        this.synced = new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <V extends IDynamic<V, ?>, S extends ISerializer<V, S>> DynamicRegistry<V, S> getRegistry(
            ResourceLocation registryName) {
        return (DynamicRegistry<V, S>) this.registries.get(registryName);
    }

    public <V extends IDynamic<V, ?>, S extends ISerializer<V, S>> DynamicRegistry<V, S> createRegistry(
            DynamicRegistryBuilder<V, S> builder) {
        Set<Class<?>> superTypes = new HashSet<>();
        this.findSuperTypes(builder.getType(), superTypes);
        SetView<Class<?>> overlaps = Sets.intersection(superTypes, this.superTypes.keySet());
        if (!overlaps.isEmpty()) {
            Class<?> foundType = overlaps.iterator().next();
            LOGGER.error(
                    "Found existing registry of type {} named {}. You cannot create a new registry ({}) with type {}, as {} is a subclass of that type.",
                    foundType, this.superTypes.get(foundType), builder.getName(), builder.getType(), builder.getType());
            throw new IllegalArgumentException("Duplicate registry parent type found " + foundType
                    + " - you can only have one registry for a particular super type.");
        }
        ResourceLocation name = builder.getName();
        DynamicRegistry<V, S> reg = new DynamicRegistry<>(builder);
        this.registries.put(name, reg);
        this.superTypes.put(builder.getType(), name);
        if (builder.shouldSave())
            this.persisted.add(name);
        if (builder.shouldSync())
            this.synced.add(name);
        return this.getRegistry(name);
    }

    public void reloadResources(Map<ResourceLocation, JsonElement> entries) {
        this.registries.values().forEach(registry -> {
            registry.unfreeze();
            registry.clear();
        });
        entries.forEach((loc, element) -> {
            String[] paths = loc.getPath().split("/", 3);
            ResourceLocation registryName = new ResourceLocation(paths[0], paths[1]);
            ResourceLocation entryName = new ResourceLocation(loc.getNamespace(), paths[2]);
            DynamicRegistry<? extends IDynamic<?, ?>, ?> registry = this.getRegistry(registryName);
            if (registry == null)
                LOGGER.error("Registry does not exist: {}", registryName);
            else
                registry.registerDynamic(entryName, element);
        });
        this.registries.values().forEach(DynamicRegistry::freeze);
        this.needsSync = true;
    }

    public DynamicRegistryPacket syncPacket() {
        return new DynamicRegistryPacket(this.synced.stream().map(this::getRegistry).collect(Collectors.toList()));
    }

    public boolean needsSync() {
        return this.needsSync;
    }

    public void synced() {
        this.needsSync = false;
    }

    private void findSuperTypes(Class<?> type, Set<Class<?>> types) {
        if (type == null || type == Object.class)
            return;
        types.add(type);
        for (Class<?> interfaceClass : type.getInterfaces())
            this.findSuperTypes(interfaceClass, types);
        this.findSuperTypes(type.getSuperclass(), types);
    }
}

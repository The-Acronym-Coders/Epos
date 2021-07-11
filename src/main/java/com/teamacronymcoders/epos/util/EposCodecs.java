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

package com.teamacronymcoders.epos.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nullable;

public final class EposCodecs {

    public static final Codec<IFormattableTextComponent> FORMATTABLE_TEXT_COMPONENT = Codec.PASSTHROUGH
            .flatXmap(dynamic -> {
                try {
                    IFormattableTextComponent component = ITextComponent.Serializer
                            .fromJson(dynamic.convert(JsonOps.INSTANCE).getValue());
                    return component != null ? DataResult.success(component, Lifecycle.stable())
                            : DataResult.error("Not a valid formattable text component, returned null.",
                            Lifecycle.stable());
                } catch (final Exception e) {
                    return DataResult.error("An error was thrown: " + e.getMessage(), Lifecycle.stable());
                }
            }, component -> {
                try {
                    JsonElement element = ITextComponent.Serializer.toJsonTree(component);
                    return DataResult.success(new Dynamic<>(JsonOps.INSTANCE, element), Lifecycle.stable());
                } catch (final Exception e) {
                    return DataResult.error("An error was thrown: " + e.getMessage(), Lifecycle.stable());
                }
            });

    public static final Codec<EffectInstance> EFFECT_INSTANCE = RecordCodecBuilder.create(instance -> instance
        .group(
                forgeRegistryEntry(ForgeRegistries.POTIONS).fieldOf("effect").forGetter(EffectInstance::getEffect),
                Codec.INT.fieldOf("duration").forGetter(EffectInstance::getDuration),
                Codec.INT.optionalFieldOf("amplifier", 1).forGetter(EffectInstance::getAmplifier),
                Codec.BOOL.optionalFieldOf("ambient", false).forGetter(EffectInstance::isAmbient),
                Codec.BOOL.optionalFieldOf("visible", true).forGetter(EffectInstance::isVisible),
                Codec.BOOL.optionalFieldOf("showIcon", true).forGetter(EffectInstance::showIcon)
        ).apply(instance, EffectInstance::new)
    );

    public static <V extends IForgeRegistryEntry<V>> Codec<IForgeRegistry<V>> forgeRegistry() {
        return ResourceLocation.CODEC.comapFlatMap(loc -> {
            @Nullable
            IForgeRegistry<V> registry = RegistryManager.ACTIVE.getRegistry(loc);
            return registry != null ? DataResult.success(registry, Lifecycle.stable())
                    : DataResult.error("The registry does not exist: " + loc, Lifecycle.stable());
        }, IForgeRegistry::getRegistryName);
    }

    public static <V extends IForgeRegistryEntry<V>> Codec<V> forgeRegistryEntry(IForgeRegistry<V> registry) {
        return ResourceLocation.CODEC.comapFlatMap(loc -> {
            @Nullable
            V val = registry.getValue(loc);
            return val != null ? DataResult.success(val, Lifecycle.stable())
                    : DataResult.error("Not a valid registry object within " + registry.getRegistryName() + ": " + loc);
        }, IForgeRegistryEntry::getRegistryName);
    }

}

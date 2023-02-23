/*
 * Copyright (c) Team Acronym Coders
 * SPDX-License-Identifier: MIT
 */

package com.teamacronymcoders.epos.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * A utility containing additional codecs for instances not associated with this
 * mod.
 */
public class EposCodecs {

    /**
     * A codec for a {@link MutableComponent} via a passthrough to a JSON.
     */
    public static final Codec<MutableComponent> MUTABLE_COMPONENT = Codec.PASSTHROUGH
            .flatXmap(dynamic -> {
                try {
                    var component = Component.Serializer.fromJson(dynamic.convert(JsonOps.INSTANCE).getValue());
                    return component != null ? DataResult.success(component)
                            : DataResult.error("'" + dynamic + "' was either null or empty.");
                } catch (RuntimeException e) {
                    return DataResult.error("An error was thrown trying to decode '" + dynamic + "': " + e.getMessage());
                }
            }, component -> {
                try {
                    var element = MutableComponent.Serializer.toJsonTree(component);
                    return DataResult.success(new Dynamic<>(JsonOps.INSTANCE, element));
                } catch (final Exception e) {
                    return DataResult.error("An error was thrown trying to encode '" + component + "': " + e.getMessage());
                }
            });
}

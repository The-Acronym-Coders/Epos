/*
 * Copyright (c) Team Acronym Coders
 * SPDX-License-Identifier: MIT
 */

package com.teamacronymcoders.epos.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.minecraft.network.chat.MutableComponent;

// TODO: Document
// TODO: Figure out how to handle expressions
// TODO: Am I doing this right?
public record Skill(MutableComponent name, MutableComponent description, int maxLevel) {
    public static final Codec<Skill> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EposCodecs.MUTABLE_COMPONENT.fieldOf("name").forGetter(Skill::name),
            EposCodecs.MUTABLE_COMPONENT.fieldOf("description").forGetter(Skill::description),
            Codec.INT.optionalFieldOf("max_level", 100).forGetter(Skill::maxLevel)
    ).apply(instance, Skill::new));
}

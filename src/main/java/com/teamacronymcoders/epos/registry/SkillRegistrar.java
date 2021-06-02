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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.skill.Skill;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class SkillRegistrar {

    public static final RegistryEntry<SkillSerializer> SKILL_SERIALIZER = Epos.instance().getRegistrate()
            .skillSerializer("skill", () -> {
                Codec<Skill> codec = RecordCodecBuilder.create(instance -> instance
                        .group(EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(Skill::getName),
                                EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description")
                                        .forGetter(Skill::getDescription),
                                Codec.intRange(1, 256).optionalFieldOf("maxLevel", 1).forGetter(Skill::getMaxLevel),
                                Codec.STRING.optionalFieldOf("expression", Skill.DEFAULT_SKILL_EXPRESSION)
                                        .forGetter(Skill::getExpression))
                        .apply(instance, Skill::new));
                return codec;
            }).register();

    public static final void register() {
    }
}

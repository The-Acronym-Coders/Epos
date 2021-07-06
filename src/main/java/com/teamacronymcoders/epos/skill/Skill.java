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

package com.teamacronymcoders.epos.skill;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.registry.SkillRegistrar;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Skill extends ForgeRegistryEntry<ISkill> implements ISkill {

    @VisibleForTesting
    public static final String DEFAULT_SKILL_EXPRESSION = "128 * (2 ^ ( (1 / 7) * (x - 1) ) )";

    public static final Codec<Skill> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(Skill::getName),
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(Skill::getDescription),
                    Codec.intRange(1, 256).optionalFieldOf("maxLevel", 1).forGetter(Skill::getMaxLevel), Codec.STRING
                            .optionalFieldOf("expression", DEFAULT_SKILL_EXPRESSION).forGetter(Skill::getExpression))
            .apply(instance, Skill::new));

    private final IFormattableTextComponent name;
    private final IFormattableTextComponent description;
    private final int maxLevel;
    private final String expression;

    public Skill(IFormattableTextComponent name, IFormattableTextComponent description, int maxLevel, String expression) {
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.expression = expression;
    }

    @Override
    public IFormattableTextComponent getName() {
        return this.name;
    }

    @Override
    public IFormattableTextComponent getDescription() {
        return this.description;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public ISerializer<? extends ISkill, ?> serializer() {
        return SkillRegistrar.SKILL_SERIALIZER.get();
    }
}

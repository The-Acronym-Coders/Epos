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
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.registry.SkillRegistrar;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class Skill implements ISkill {

    @VisibleForTesting
    public static final String DEFAULT_SKILL_EXPRESSION = "128 * (2 ^ ( (1 / 7) * (x - 1) ) )";

    public static final Codec<Skill> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(Skill::getName),
                    EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(Skill::getDescription),
                    Codec.intRange(1, 256).optionalFieldOf("maxLevel", 1).forGetter(Skill::getMaxLevel),
                    Codec.STRING.optionalFieldOf("expression", DEFAULT_SKILL_EXPRESSION).forGetter(Skill::getExpression))
            .apply(instance, Skill::new));

    private final NonNullSupplier<MutableComponent> name;
    private final NonNullSupplier<MutableComponent> description;
    private final NonNullSupplier<Integer> maxLevel;
    private final NonNullSupplier<String> expression;
    private final NonNullSupplier<Codec<? extends ISkill>> skillCodec;

    private String translationKey;

    /*
    *
    * Default Codec Constructor
    *
    */
    public Skill(@Nonnull MutableComponent name, @Nonnull MutableComponent description, @Nonnull int maxLevel, @Nonnull String expression) {
        this.name = () -> name;
        this.description = () -> description;
        this.maxLevel = () -> maxLevel;
        this.expression = () -> expression;
        this.skillCodec = SkillRegistrar.SKILL_SERIALIZER;
    }

    /*
     *
     * Default Constructor
     *
     */
    public Skill(@Nonnull MutableComponent name, @Nonnull MutableComponent description, @Nonnull int maxLevel, @Nonnull String expression, Codec<? extends ISkill> skillCodec) {
        this.name = () -> name;
        this.description = () -> description;
        this.maxLevel = () -> maxLevel;
        this.expression = () -> expression;
        if (skillCodec == null) {
            this.skillCodec = SkillRegistrar.SKILL_SERIALIZER;
        } else {
            this.skillCodec = () -> skillCodec;
        }
    }

    /*
     *
     * Default Registrate Constructor
     *
     */
    public Skill(NonNullSupplier<MutableComponent> name, NonNullSupplier<MutableComponent> description, NonNullSupplier<Integer> maxLevel, NonNullSupplier<String> expression, NonNullSupplier<Codec<? extends ISkill>> skillCodec) {
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.expression = expression;
        if (skillCodec == null) {
            this.skillCodec = SkillRegistrar.SKILL_SERIALIZER;
        } else {
            this.skillCodec = skillCodec;
        }
    }

    @Override
    public MutableComponent getName() {
        return this.name.get();
    }

    @Override
    public MutableComponent getDescription() {
        return this.description.get();
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel.get();
    }

    @Override
    public String getExpression() {
        return this.expression.get();
    }

    @Override
    public @NotNull String getTranslationKey(ResourceLocation id) {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("skill", id);
        }
        return this.translationKey;
    }

    @Override
    public Codec<? extends ISkill> codec() {
        return this.skillCodec.get();
    }

}

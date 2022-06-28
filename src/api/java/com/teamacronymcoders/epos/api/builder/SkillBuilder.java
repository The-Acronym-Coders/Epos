package com.teamacronymcoders.epos.api.builder;

import com.mojang.datafixers.util.Function4;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.skill.Skill;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.NonNullSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillBuilder {

    public static final Map<ResourceLocation, ISkill> BUILT_SKILLS = new HashMap<>();

    public static SkillBuilder create(ResourceLocation registryName) {
        return new SkillBuilder(registryName, Skill::new);
    }

    private final ResourceLocation registryName;
    private final Function4<MutableComponent, MutableComponent, Integer, String, ISkill> factory;
    private NonNullSupplier<MutableComponent> name;
    private NonNullSupplier<MutableComponent> description;
    private NonNullSupplier<Integer> maxLevel = () -> 1;
    private NonNullSupplier<String> expression = () -> Skill.DEFAULT_SKILL_EXPRESSION;

    public SkillBuilder(ResourceLocation registryName, Function4<MutableComponent, MutableComponent, Integer, String, ISkill> factory) {
        this.registryName = registryName;
        this.factory = factory;
    }

    public SkillBuilder name(NonNullSupplier<MutableComponent> name) {
        this.name = name;
        return this;
    }

    public SkillBuilder description(NonNullSupplier<MutableComponent> description) {
        this.description = description;
        return this;
    }

    public SkillBuilder maxLevel(NonNullSupplier<Integer> maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public SkillBuilder withExpression(NonNullSupplier<String> expression) {
        this.expression = expression;
        return this;
    }

    @NonnullType
    public ISkill createEntry() {
        ISkill skill = factory.apply(this.name.get(), this.description.get(), this.maxLevel.get(), this.expression.get());
        BUILT_SKILLS.put(this.registryName, skill);
        return skill;
    }

}

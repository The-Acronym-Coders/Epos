package com.teamacronymcoders.epos.api.builder;

import com.mojang.datafixers.util.Function4;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.skill.Skill;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

import java.util.ArrayList;
import java.util.List;

public class SkillBuilder {

    public static final List<ISkill> BUILT_SKILLS = new ArrayList<>();

    public static SkillBuilder create(ResourceLocation registryName) {
        return new SkillBuilder(registryName, Skill::new);
    }

    private final ResourceLocation registryName;
    private final Function4<IFormattableTextComponent, IFormattableTextComponent, Integer, String, ISkill> factory;
    private NonNullSupplier<IFormattableTextComponent> name;
    private NonNullSupplier<IFormattableTextComponent> description;
    private NonNullSupplier<Integer> maxLevel = () -> 1;
    private NonNullSupplier<String> expression = () -> Skill.DEFAULT_SKILL_EXPRESSION;

    public SkillBuilder(ResourceLocation registryName, Function4<IFormattableTextComponent, IFormattableTextComponent, Integer, String, ISkill> factory) {
        this.registryName = registryName;
        this.factory = factory;
    }

    public SkillBuilder name(NonNullSupplier<IFormattableTextComponent> name) {
        this.name = name;
        return this;
    }

    public SkillBuilder description(NonNullSupplier<IFormattableTextComponent> description) {
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
        skill.setRegistryName(this.registryName);
        BUILT_SKILLS.add(skill);
        return skill;
    }

}

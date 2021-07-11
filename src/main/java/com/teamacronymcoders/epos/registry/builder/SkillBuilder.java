package com.teamacronymcoders.epos.registry.builder;

import com.mojang.datafixers.util.Function4;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.skill.Skill;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

import java.util.ArrayList;
import java.util.List;

public class SkillBuilder<T extends ISkill, P> extends AbstractBuilder<ISkill, T, P, SkillBuilder<T, P>> {

    public static final List<ISkill> BUILT_SKILLS = new ArrayList<>();

    public static <P> SkillBuilder<Skill, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
        return new SkillBuilder<>(owner, parent, name, callback, Skill::new);
    }

    private final Function4<IFormattableTextComponent, IFormattableTextComponent, Integer, String, T> factory;
    private NonNullSupplier<IFormattableTextComponent> name;
    private NonNullSupplier<IFormattableTextComponent> description;
    private NonNullSupplier<Integer> maxLevel = () -> 1;
    private NonNullSupplier<String> expression = () -> Skill.DEFAULT_SKILL_EXPRESSION;

    public SkillBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, Function4<IFormattableTextComponent, IFormattableTextComponent, Integer, String, T> factory) {
        super(owner, parent, name, callback, ISkill.class);
        this.factory = factory;
    }

    public SkillBuilder<T, P> name(NonNullSupplier<IFormattableTextComponent> name) {
        this.name = name;
        return this;
    }

    public SkillBuilder<T, P> description(NonNullSupplier<IFormattableTextComponent> description) {
        this.description = description;
        return this;
    }

    public SkillBuilder<T, P> maxLevel(NonNullSupplier<Integer> maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public SkillBuilder<T, P> withExpression(NonNullSupplier<String> expression) {
        this.expression = expression;
        return this;
    }

    @Override
    @NonnullType
    public T createEntry() {
        T skill = factory.apply(this.name.get(), this.description.get(), this.maxLevel.get(), this.expression.get());
        skill.setRegistryName(new ResourceLocation(getOwner().getModid(), getName()));
        BUILT_SKILLS.add(skill);
        return skill;
    }

}

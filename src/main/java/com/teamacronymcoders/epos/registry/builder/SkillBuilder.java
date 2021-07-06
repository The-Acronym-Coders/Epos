package com.teamacronymcoders.epos.registry.builder;

import com.mojang.datafixers.util.Function4;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.skill.Skill;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.util.text.IFormattableTextComponent;

public class SkillBuilder<T extends ISkill, P> extends AbstractBuilder<ISkill, T, P, SkillBuilder<T, P>> {

    public static <P> SkillBuilder<Skill, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
        return new SkillBuilder<>(owner, parent, name, callback, Skill::new);
    }

    private final Function4<IFormattableTextComponent, IFormattableTextComponent, Integer, String, T> factory;
    private NonNullSupplier<IFormattableTextComponent> name;
    private NonNullSupplier<IFormattableTextComponent> description;
    private NonNullSupplier<Integer> maxLevel;
    private NonNullSupplier<String> expression;

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
    protected @NonnullType T createEntry() {
        return factory.apply(this.name.get(), this.description.get(), this.maxLevel.get(), this.expression.get());
    }
}

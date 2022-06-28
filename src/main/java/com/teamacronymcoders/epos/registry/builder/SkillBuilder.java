package com.teamacronymcoders.epos.registry.builder;

import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.registry.SkillRegistrar;
import com.teamacronymcoders.epos.skill.Skill;
import com.teamacronymcoders.epos.util.EposRegistries;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class SkillBuilder<T extends ISkill, P> extends AbstractBuilder<ISkill, T, P, SkillBuilder<T, P>> {

  private final Function5<NonNullSupplier<MutableComponent>, NonNullSupplier<MutableComponent>, NonNullSupplier<Integer>, NonNullSupplier<String>, NonNullSupplier<Codec<? extends ISkill>>, T> creator;
  private NonNullSupplier<MutableComponent> name;
  private NonNullSupplier<MutableComponent> description;
  private NonNullSupplier<Integer> maxLevel;
  private NonNullSupplier<String> expression;
  private NonNullSupplier<Codec<? extends ISkill>> skillCodec;

  public SkillBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceKey<Registry<ISkill>> registryKey,
                      Function5<NonNullSupplier<MutableComponent>, NonNullSupplier<MutableComponent>, NonNullSupplier<Integer>, NonNullSupplier<String>, NonNullSupplier<Codec<? extends ISkill>>, T> creator) {
    super(owner, parent, name, callback, registryKey);
    this.creator = creator;
    this.name = () -> Component.literal("empty");
    this.description = () -> Component.literal("empty");
    this.maxLevel = () -> 99;
    this.expression = () -> "128 * (2 ^ ((1 / 7) * (x - 1)))";
    this.skillCodec = SkillRegistrar.SKILL_SERIALIZER;
  }

  // Main Builder Methods
  public SkillBuilder<T, P> name(NonNullSupplier<MutableComponent> name) {
    this.name = name;
    return this;
  }

  public SkillBuilder<T, P> description(NonNullSupplier<MutableComponent> description) {
    this.description = description;
    return this;
  }

  public SkillBuilder<T, P> maxLevel(NonNullSupplier<Integer> maxLevel) {
    this.maxLevel = maxLevel;
    return this;
  }

  public SkillBuilder<T, P> expression(NonNullSupplier<String> expression) {
    this.expression = expression;
    return this;
  }

  public SkillBuilder<T, P> skillCodec(NonNullSupplier<Codec<? extends ISkill>> skillCodec) {
    this.skillCodec = skillCodec;
    return this;
  }

  // Misc Builder Methods
  public SkillBuilder<T, P> lang(String lang) {
    return this.lang(t -> t.getTranslationKey(new ResourceLocation(getOwner().getModid(), getName())), lang);
  }

  @Override
  protected @NonnullType T createEntry() {
    return creator.apply(name, description, maxLevel, expression, skillCodec);
  }

  public static <P> SkillBuilder<Skill, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
    return new SkillBuilder<>(owner, parent, name, callback, EposRegistries.Keys.SKILLS, Skill::new);
  }

  public static <P extends AbstractRegistrate<P>> NonNullBiFunction<String, BuilderCallback, SkillBuilder<Skill, P>> entry(Supplier<P> owner) {
    return (name, builderCallback) -> create(owner.get(), owner.get(), name, builderCallback);
  }

}

package com.teamacronymcoders.epos.registry.builder;

import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.feat.Feat;
import com.teamacronymcoders.epos.registry.FeatRegistrar;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FeatBuilder<T extends IFeat, P> extends AbstractBuilder<IFeat, T, P, FeatBuilder<T, P>> {
  private final Function5<NonNullSupplier<MutableComponent>, NonNullSupplier<MutableComponent>, NonNullSupplier<Boolean>, List<EventManager.ISubscribe>, NonNullSupplier<Codec<? extends IFeat>>, T> creator;
  private NonNullSupplier<MutableComponent> name;
  private NonNullSupplier<MutableComponent> description;
  private NonNullSupplier<Boolean> isAbility;
  private final List<EventManager.ISubscribe> subscribables;
  private NonNullSupplier<Codec<? extends IFeat>> featCodec;

  public FeatBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceKey<Registry<IFeat>> registryKey,
                     Function5<NonNullSupplier<MutableComponent>, NonNullSupplier<MutableComponent>, NonNullSupplier<Boolean>, List<EventManager.ISubscribe>, NonNullSupplier<Codec<? extends IFeat>>, T> creator) {
    super(owner, parent, name, callback, registryKey);
    this.creator = creator;
    this.name = () -> Component.literal("Empty");
    this.description = () -> Component.literal("Empty");
    this.isAbility = () -> false;
    this.subscribables = new ArrayList<>();
    this.featCodec = FeatRegistrar.DEFAULT_FEAT_SERIALIZER;
  }

  // Main Builder Methods
  public FeatBuilder<T, P> name(NonNullSupplier<MutableComponent> name) {
    this.name = name;
    return this;
  }

  public FeatBuilder<T, P> description(NonNullSupplier<MutableComponent> description) {
    this.description = description;
    return this;
  }

  public FeatBuilder<T, P> isAbility(NonNullSupplier<Boolean> isAbility) {
    this.isAbility = isAbility;
    return this;
  }

  public FeatBuilder<T, P> addEventHandler(EventManager.ISubscribe subscribable) {
    this.subscribables.add(subscribable);
    return this;
  }

  public FeatBuilder<T, P> featCodec(NonNullSupplier<Codec<? extends IFeat>> featCodec) {
    this.featCodec = featCodec;
    return this;
  }

  // Misc Builder Methods
  public FeatBuilder<T, P> lang(String lang) {
    return this.lang(t -> t.getTranslationKey(new ResourceLocation(getOwner().getModid(), getName())), lang);
  }

  // Core/Utility Methods
  @Override
  protected @NonnullType T createEntry() {
    return creator.apply(name, description, isAbility, subscribables, featCodec);
  }

  public static <P> FeatBuilder<Feat, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
    return new FeatBuilder<>(owner, parent, name, callback, EposRegistries.Keys.FEATS, Feat::new);
  }

  public static <P extends AbstractRegistrate<P>>NonNullBiFunction<String, BuilderCallback, FeatBuilder<Feat, P>> entry(Supplier<P> owner) {
    return (name, builderCallback) -> create(owner.get(), owner.get(), name, builderCallback);
  }
}

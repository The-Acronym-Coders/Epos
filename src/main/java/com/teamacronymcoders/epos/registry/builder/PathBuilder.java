package com.teamacronymcoders.epos.registry.builder;

import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.path.Path;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.registry.PathRegistrar;
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

public class PathBuilder<T extends IPath, P> extends AbstractBuilder<IPath, T, P, PathBuilder<T, P>> {

  private final Function5<NonNullSupplier<MutableComponent>, NonNullSupplier<MutableComponent>, NonNullSupplier<Integer>, NonNullSupplier<PathFeatures>, NonNullSupplier<Codec<? extends IPath>>, T> creator;
  private NonNullSupplier<MutableComponent> name;
  private NonNullSupplier<MutableComponent> description;
  private NonNullSupplier<Integer> maxLevel;
  private final NonNullSupplier<PathFeatures> pathFeatures;
  private NonNullSupplier<Codec<? extends IPath>> pathCodec;

  public PathBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceKey<Registry<IPath>> registryKey,
                     Function5<NonNullSupplier<MutableComponent>, NonNullSupplier<MutableComponent>, NonNullSupplier<Integer>, NonNullSupplier<PathFeatures>, NonNullSupplier<Codec<? extends IPath>>, T> creator) {
    super(owner, parent, name, callback, registryKey);
    this.creator = creator;
    this.name = () -> Component.literal("empty");
    this.description = () -> Component.literal("empty");
    this.maxLevel = () -> 10;
    this.pathFeatures = PathFeatures::new;
    this.pathCodec = PathRegistrar.PATH_SERIALIZER;
  }


  // Main Builder Methods
  public PathBuilder<T, P> name(NonNullSupplier<MutableComponent> name) {
    this.name = name;
    return this;
  }

  public PathBuilder<T, P> description(NonNullSupplier<MutableComponent> description) {
    this.description = description;
    return this;
  }

  public PathBuilder<T, P> maxLevel(NonNullSupplier<Integer> maxLevel) {
    this.maxLevel = maxLevel;
    return this;
  }

  public PathBuilder<T, P> withFeature(int level, AbstractPathFeature pathFeature) {
    if (level > this.maxLevel.get()) {
      Epos.getLogger().warn("Attempted to add PathFeature for Level: " + level + " when Max-Level is: " + this.maxLevel);
      return this;
    }

    this.pathFeatures.get().getFeaturesForLevel(level).add(pathFeature);
    return this;
  }

  public PathBuilder<T, P> pathCodec(NonNullSupplier<Codec<? extends IPath>> pathCodec) {
    this.pathCodec = pathCodec;
    return this;
  }

  // Misc Builder Methods
  public PathBuilder<T, P> lang(String lang) {
    return this.lang(t -> t.getTranslationKey(new ResourceLocation(getOwner().getModid(), getName())), lang);
  }

  @Override
  protected @NonnullType T createEntry() {
    return creator.apply(name, description, maxLevel, pathFeatures, pathCodec);
  }

  public static <P> PathBuilder<Path, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
    return new PathBuilder<>(owner, parent, name, callback, EposRegistries.Keys.PATHS, Path::new);
  }

  public static <P extends AbstractRegistrate<P>> NonNullBiFunction<String, BuilderCallback, PathBuilder<Path, P>> entry(Supplier<P> owner) {
    return (name, builderCallback) -> create(owner.get(), owner.get(), name, builderCallback);
  }

}

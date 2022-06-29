package com.teamacronymcoders.epos.registry.builder;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.SpiritualAid;
import com.teamacronymcoders.epos.util.EposRegistries;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class SpiritualAidBuilder<T extends ISpiritualAid, P> extends AbstractBuilder<ISpiritualAid, T, P, SpiritualAidBuilder<T, P>> {

  private final BiFunction<NonNullSupplier<ResourceLocation>, NonNullSupplier<List<MobEffectInstance>>, T> creator;

  private NonNullSupplier<ResourceLocation> entityID;
  private final NonNullSupplier<List<MobEffectInstance>> effects;

  public SpiritualAidBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceKey<Registry<ISpiritualAid>> registryKey,
                             BiFunction<NonNullSupplier<ResourceLocation>, NonNullSupplier<List<MobEffectInstance>>, T> creator) {
    super(owner, parent, name, callback, registryKey);
    this.creator = creator;
    this.entityID = () -> new ResourceLocation(Epos.ID, "missing");
    this.effects = ArrayList::new;
  }

  public SpiritualAidBuilder<T, P> entityID(NonNullSupplier<ResourceLocation> entityID) {
    this.entityID = entityID;
    return this;
  }

  public SpiritualAidBuilder<T, P> entity(NonNullSupplier<EntityType<?>> entity) {
    this.entityID = () -> ForgeRegistries.ENTITIES.getKey(entity.get());
    return this;
  }

  public SpiritualAidBuilder<T, P> addEffect(NonNullSupplier<MobEffectInstance> instance) {
    this.effects.get().add(instance.get());
    return this;
  }

  @Override
  protected @NonnullType T createEntry() {
    return creator.apply(entityID, effects);
  }

  public static <P> SpiritualAidBuilder<SpiritualAid, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
    return new SpiritualAidBuilder<>(owner, parent, name, callback, EposRegistries.Keys.SPIRITUAL_AIDS, SpiritualAid::new);
  }

  public static <P extends AbstractRegistrate<P>> NonNullBiFunction<String, BuilderCallback, SpiritualAidBuilder<SpiritualAid, P>> entry(Supplier<P> owner) {
    return (name, builderCallback) -> create(owner.get(), owner.get(), name, builderCallback);
  }

}

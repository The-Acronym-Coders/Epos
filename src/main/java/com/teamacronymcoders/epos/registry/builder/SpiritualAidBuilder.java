package com.teamacronymcoders.epos.registry.builder;

import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class SpiritualAidBuilder<T extends ISpiritualAid, P> extends AbstractBuilder<ISpiritualAid, T, P, SpiritualAidBuilder<T, P>> {

  public SpiritualAidBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceKey<Registry<ISpiritualAid>> registryKey) {
    super(owner, parent, name, callback, registryKey);
  }

  @Override
  protected @NonnullType T createEntry() {
    return null;
  }

}

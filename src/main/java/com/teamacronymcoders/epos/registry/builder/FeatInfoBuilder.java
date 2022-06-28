package com.teamacronymcoders.epos.registry.builder;

import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FeatInfoBuilder<T extends FeatInfo, P> extends AbstractBuilder<FeatInfo, T, P, FeatInfoBuilder<T, P>> {

  public FeatInfoBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceKey<Registry<FeatInfo>> registryKey) {
    super(owner, parent, name, callback, registryKey);
  }

  @Override
  protected @NonnullType T createEntry() {
    return null;
  }

}

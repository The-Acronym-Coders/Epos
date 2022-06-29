package com.teamacronymcoders.epos.api.charactersheet.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.ExtendableFeatInfo;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.IExtendableFeatInfo;
import com.teamacronymcoders.epos.api.registry.EposRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record FeatStorage(Map<ResourceLocation, IExtendableFeatInfo> feats) {
  public static final Codec<FeatStorage> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(Codec.unboundedMap(ResourceLocation.CODEC, EposRegistries.FEAT_INFOS.get().getCodec()).fieldOf("feats").forGetter(FeatStorage::feats))
          .apply(instance, FeatStorage::new));

  public IExtendableFeatInfo getSkill(ResourceLocation featId) {
    return this.getOrCreate(featId);
  }

  private IExtendableFeatInfo getOrCreate(ResourceLocation featId) {
    // Todo: Rewrite to support max level of Skills
    return this.feats.computeIfAbsent(featId, (rl) -> new ExtendableFeatInfo(false));
  }



}

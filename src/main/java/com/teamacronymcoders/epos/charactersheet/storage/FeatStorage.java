package com.teamacronymcoders.epos.charactersheet.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.IFeatStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.FeatInfo;
import com.teamacronymcoders.epos.api.registry.EposAPIRegistries;
import com.teamacronymcoders.epos.charactersheet.storage.info.ImplExtendableFeatInfo;
import com.teamacronymcoders.epos.registry.EposRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record FeatStorage(Map<ResourceLocation, FeatInfo> feats) implements IFeatStorage {
  public static final Codec<FeatStorage> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(Codec.unboundedMap(ResourceLocation.CODEC, EposRegistries.FEAT_INFOS.get().getCodec()).fieldOf("feats").forGetter(FeatStorage::feats))
          .apply(instance, FeatStorage::new));

  @Override
  public FeatInfo getSkill(ResourceLocation featId) {
    return this.getOrCreate(featId);
  }

  private FeatInfo getOrCreate(ResourceLocation featId) {
    // Todo: Rewrite to support max level of Skills
    return this.feats.computeIfAbsent(featId, (rl) -> new ImplExtendableFeatInfo(false));
  }



}

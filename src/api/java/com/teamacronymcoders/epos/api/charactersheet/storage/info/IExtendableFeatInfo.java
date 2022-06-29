package com.teamacronymcoders.epos.api.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.registry.EposRegistries;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface IExtendableFeatInfo {
  /**
   * Codec for (de)serializing biome modifiers inline.
   * Mods can use this for data generation.
   */
  Codec<IExtendableFeatInfo> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> EposRegistries.FEAT_INFO_SERIALIZERS.get().getCodec())
          .dispatch(IExtendableFeatInfo::codec, Function.identity());

  boolean isUnlocked();

  Codec<? extends IExtendableFeatInfo> codec();
}

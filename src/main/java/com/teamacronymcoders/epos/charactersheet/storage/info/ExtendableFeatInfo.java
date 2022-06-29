package com.teamacronymcoders.epos.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.FeatInfo;
import com.teamacronymcoders.epos.registry.EposRegistries;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface ExtendableFeatInfo extends FeatInfo {

  /**
   * Codec for (de)serializing feat infos inline.
   * Mods can use this for data generation.
   */
  Codec<FeatInfo> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> EposRegistries.FEAT_INFO_SERIALIZERS.get().getCodec())
          .dispatch(FeatInfo::codec, Function.identity());

}

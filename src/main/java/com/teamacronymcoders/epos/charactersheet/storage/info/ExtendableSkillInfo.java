package com.teamacronymcoders.epos.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.SkillInfo;
import com.teamacronymcoders.epos.registry.EposRegistries;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Function;

public interface ExtendableSkillInfo extends SkillInfo {
  /**
   * Codec for (de)serializing feat infos inline.
   * Mods can use this for data generation.
   */
  Codec<SkillInfo> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> EposRegistries.SKILL_INFO_SERIALIZERS.get().getCodec())
          .dispatch(SkillInfo::codec, Function.identity());
}

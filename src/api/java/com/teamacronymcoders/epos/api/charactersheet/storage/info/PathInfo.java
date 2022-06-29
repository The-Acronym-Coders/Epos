package com.teamacronymcoders.epos.api.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PathInfo(int level, boolean isUnlocked) {
  public static final Codec<PathInfo> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(
                  Codec.INT.fieldOf("level").forGetter(PathInfo::level),
                  Codec.BOOL.fieldOf("isUnlocked").forGetter(PathInfo::isUnlocked)
          ).apply(instance, PathInfo::new));
}

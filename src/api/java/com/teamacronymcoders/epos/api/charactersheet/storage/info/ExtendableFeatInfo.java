package com.teamacronymcoders.epos.api.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ExtendableFeatInfo implements IExtendableFeatInfo {
  public static final Codec<IExtendableFeatInfo> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(Codec.BOOL.fieldOf("isUnlocked").forGetter(IExtendableFeatInfo::isUnlocked))
          .apply(instance, ExtendableFeatInfo::new));

  private boolean isUnlocked;

  public ExtendableFeatInfo() {
    this.isUnlocked = false;
  }

  public ExtendableFeatInfo(boolean isUnlocked) {
    this.isUnlocked = false;
  }

  public boolean isUnlocked() {
    return isUnlocked;
  }

  @Override
  public Codec<? extends IExtendableFeatInfo> codec() {
    return CODEC;
  }
}

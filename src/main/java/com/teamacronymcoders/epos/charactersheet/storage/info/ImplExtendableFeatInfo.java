package com.teamacronymcoders.epos.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.FeatInfo;

/**
 *
 */
public class ImplExtendableFeatInfo implements FeatInfo {
  /**
   * The base {@link Codec} implementation for the default FeatInfo.
   */
  public static final Codec<FeatInfo> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(Codec.BOOL.fieldOf("isUnlocked").forGetter(FeatInfo::isUnlocked))
          .apply(instance, ImplExtendableFeatInfo::new));

  /**
   * The "unlocked" state of the Feat on the player.
   */
  private boolean isUnlocked;

  /**
   * Default Constructor
   */
  public ImplExtendableFeatInfo() {
    this.isUnlocked = false;
  }

  /**
   * Codec Constructor
   *
   * @param isUnlocked See {@link ImplExtendableFeatInfo#isUnlocked}
   */
  public ImplExtendableFeatInfo(boolean isUnlocked) {
    this.isUnlocked = isUnlocked;
  }

  /**
   * @return Returns {@link ImplExtendableFeatInfo#isUnlocked}
   */
  public boolean isUnlocked() {
    return isUnlocked;
  }

  /**
   * <p>Changes the state of {@link ImplExtendableFeatInfo#isUnlocked} from false -> true.</p>
   */
  public void unlock() {
    if (!isUnlocked) this.isUnlocked = true;
  }

  /**
   * @return See {@link FeatInfo#codec()}
   */
  @Override
  public Codec<? extends FeatInfo> codec() {
    return CODEC;
  }
}

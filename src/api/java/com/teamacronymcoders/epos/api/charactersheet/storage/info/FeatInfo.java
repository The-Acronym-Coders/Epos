package com.teamacronymcoders.epos.api.charactersheet.storage.info;

import com.mojang.serialization.Codec;

/**
 *
 */
public interface FeatInfo {

  /**
   * <p>Gets the current "Unlocked" state of the Feat for the player.</p>
   *
   * @return The unlocked state of the Feat on the player.
   */
  boolean isUnlocked();

  /**
   * <p>Gets the associated codec for the FeatInfo instance.</p>
   *
   * @return The associated codec for the FeatInfo instance.
   */
  Codec<? extends FeatInfo> codec();
}

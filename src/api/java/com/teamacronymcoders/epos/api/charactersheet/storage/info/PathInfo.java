package com.teamacronymcoders.epos.api.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.path.IPath;

public class PathInfo {

  /**
   * The base {@link Codec} implementation for the default {@link PathInfo}.
   */
  public static final Codec<PathInfo> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(
                  Codec.INT.fieldOf("level").forGetter(PathInfo::getLevel),
                  Codec.BOOL.fieldOf("isUnlocked").forGetter(PathInfo::isUnlocked)
          ).apply(instance, PathInfo::new));

  /**
   * The current level of the {@link IPath}
   */
  private int level;

  /**
   * The current "unlockable" state of the {@link IPath}.
   */
  private boolean isUnlocked;

  /**
   * <p>Default Constructor</p>
   */
  public PathInfo() {
    this.level = 0;
    this.isUnlocked = false;
  }

  /**
   * <p>Codec Constructor</p>
   *
   * @param level The current path level.
   * @param isUnlocked The current unlocked status of the path.
   */
  public PathInfo(int level, boolean isUnlocked) {
    this.level = level;
    this.isUnlocked = isUnlocked;
  }

  /**
   * <p>Gets the value of {@link PathInfo#level}.</p>
   *
   * @return The current level of the {@link IPath}.
   */
  public int getLevel() {
    return level;
  }

  /**
   * <p>Used to level-up a specific {@link IPath}.</p>
   *
   * @param levels The amount of levels to level-up by.
   */
  public void levelUp(int levels) {
    if (this.isUnlocked()) unlock();
    // Todo: Figure out utility methods for calculating the new experience values
    // Todo: Rewrite to support max level of Skills
    this.level = Math.min(this.getLevel() + levels, 10);
  }

  /**
   * <p>Used to level-down a specific {@link IPath}.</p>
   *
   * @param levels The amount of levels to level-down by.
   */
  public void levelDown(int levels) {
    // Todo: Figure out utility methods for calculating the new experience values
    // Todo: Rewrite to support max level of Skills
    this.level = Math.max(this.getLevel() - levels, 0);
  }

  /**
   * @return Returns {@link PathInfo#isUnlocked}.
   */
  public boolean isUnlocked() {
    return isUnlocked;
  }

  /**
   * <p>Changes the state of {@link PathInfo#isUnlocked} from false -> true.</p>
   */
  public void unlock() {
    if (!isUnlocked) {
      this.isUnlocked = true;
      this.levelUp(1);
    }
  }
}

package com.teamacronymcoders.epos.api.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.resources.ResourceLocation;

/**
 *
 */
public interface SkillInfo {

  /**
   * @return Returns the skill {@link ResourceLocation} id value of the {@link SkillInfo} object.
   */
  ResourceLocation getSkill();

  /**
   * @return Returns the level value of the {@link SkillInfo} object.
   */
  int getLevel();

  /**
   * <p>Used to level-up a specific {@link ISkill}.</p>
   *
   * @param levels The amount of levels to level-up by.
   */
  void levelUp(int levels);

  /**
   * <p>Used to level-down a specific {@link ISkill}.</p>
   *
   * @param levels The amount of levels to level-down by.
   */
  void levelDown(int levels);

  /**
   * @return Returns the experience value of the {@link SkillInfo} object.
   */
  int getExperience();

  /**
   * Used to add experience to the specific {@link ISkill}.
   *
   * @param exp The amount of experience to add.
   */
  void addExperience(int exp);

  /**
   * Used to remove experience from the specific {@link ISkill}.
   *
   * @param exp The amount of experience to remove.
   */
  void removeExperience(int exp);

  /**
   * @return Returns the value of {@link SkillInfo#isUnlocked()}.
   */
  boolean isUnlocked();

  /**
   * <p>Changes the state of {@link SkillInfo#isUnlocked} from false -> true.</p>
   */
  void unlock();

  /**
   * <p>Gets the associated codec for the {@link SkillInfo} instance.</p>
   *
   * @return The associated codec for the {@link SkillInfo} instance.
   */
  Codec<? extends SkillInfo> codec();
}

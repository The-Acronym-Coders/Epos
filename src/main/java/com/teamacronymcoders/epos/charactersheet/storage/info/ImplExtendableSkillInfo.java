package com.teamacronymcoders.epos.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.SkillInfo;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.resources.ResourceLocation;

/**
 *
 */
public class ImplExtendableSkillInfo implements ExtendableSkillInfo {

  /**
   * The base {@link Codec} implementation for the default {@link ImplExtendableSkillInfo}.
   */
  public static final Codec<ImplExtendableSkillInfo> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(ResourceLocation.CODEC.fieldOf("skill").forGetter(ImplExtendableSkillInfo::getSkill),
                  Codec.INT.fieldOf("level").forGetter(ImplExtendableSkillInfo::getLevel),
                  Codec.INT.fieldOf("experience").forGetter(ImplExtendableSkillInfo::getExperience),
                  Codec.BOOL.fieldOf("isUnlocked").forGetter(ImplExtendableSkillInfo::isUnlocked))
          .apply(instance, ImplExtendableSkillInfo::new));

  /**
   * The current {@link ResourceLocation} id of the referenced {@link ISkill}.
   */
  private final ResourceLocation skill;

  /**
   * The current level value of the {@link ISkill}.
   */
  private int level;

  /**
   * The current experience value of the {@link ISkill}.
   */
  private int experience;

  /**
   * The current "unlockable" state of the {@link ISkill}.
   */
  private boolean isUnlocked;

  /**
   * <p>Default Constructor</p>
   *
   * @param skill See {@link ImplExtendableSkillInfo#skill}.
   */
  public ImplExtendableSkillInfo(ResourceLocation skill) {
    this.skill = skill;
    this.level = 0;
    this.experience = 0;
    this.isUnlocked = false;
  }

  /**
   * <p>Codec Constructor</p>
   *
   * @param skill See {@link ImplExtendableSkillInfo#skill}.
   * @param level See {@link ImplExtendableSkillInfo#level}.
   * @param experience See {@link ImplExtendableSkillInfo#experience}.
   * @param isUnlocked See {@link ImplExtendableSkillInfo#isUnlocked}.
   */
  public ImplExtendableSkillInfo(ResourceLocation skill, int level, int experience, boolean isUnlocked) {
    this.skill = skill;
    this.level = level;
    this.experience = experience;
    this.isUnlocked = isUnlocked;
  }

  /**
   * @return See {@link SkillInfo#getSkill()}.
   */
  public ResourceLocation getSkill() {
    return this.skill;
  }

  /**
   * @return See {@link SkillInfo#getLevel()}.
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * <p>See {@link SkillInfo#levelUp(int)}.</p>
   *
   * @param levels The amount of levels to level-up by.
   */
  public void levelUp(int levels) {
    // Todo: Figure out utility methods for calculating the new experience values
    // Todo: Rewrite to support max level of Skills
    this.level = Math.min(this.getLevel() + levels, 10);
  }

  /**
   * <p>See {@link SkillInfo#levelDown(int)}.</p>
   *
   * @param levels The amount of levels to level-down by.
   */
  public void levelDown(int levels) {
    // Todo: Figure out utility methods for calculating the new experience values
    // Todo: Rewrite to support max level of Skills
    this.level = Math.max(this.getLevel() - levels, 0);
  }

  /**
   * @return See {@link SkillInfo#getExperience()}.
   */
  public int getExperience() {
    return this.experience;
  }

  /**
   * <p>See {@link SkillInfo#addExperience(int)}</p>
   *
   * @param exp The amount of experience to add.
   */
  public void addExperience(int exp) {
    if (experience == 0) this.unlock();
    // Todo: Figure out utility methods for calculating the new level values
    // Todo: Rewrite to support max experience of Skills
    this.experience = Math.min(this.getExperience() + exp, Integer.MAX_VALUE - 1);
  }

  /**
   * <p>See {@link SkillInfo#removeExperience(int)}.</p>
   *
   * @param exp The amount of experience to remove.
   */
  public void removeExperience(int exp) {
    // Todo: Figure out utility methods for calculating the new level values
    // Todo: Rewrite to support max experience of Skills
    this.experience = Math.max(this.getExperience() - exp, 0);
  }

  /**
   * @return See {@link SkillInfo#isUnlocked()}.
   */
  public boolean isUnlocked() {
    return this.isUnlocked;
  }

  /**
   * <p>See {@link SkillInfo#unlock()}.</p>
   */
  public void unlock() {
    this.isUnlocked = true;
    this.levelUp(1);
  }

  @Override
  public Codec<? extends SkillInfo> codec() {
    return CODEC;
  }
}

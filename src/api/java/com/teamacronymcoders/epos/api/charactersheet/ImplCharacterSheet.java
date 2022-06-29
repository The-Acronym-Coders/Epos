package com.teamacronymcoders.epos.api.charactersheet;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.FeatStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.PathStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.PointStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.SkillStorage;

/**
 * <p>Implementation Spec of {@link CharacterSheet}</p>
 */
public class ImplCharacterSheet implements CharacterSheet {

  public static final Codec<ImplCharacterSheet> DEFAULT_CODEC = RecordCodecBuilder.create(instance -> instance
          .group(
                Codec.INT.fieldOf("maxLevel").forGetter(ImplCharacterSheet::getMaxLevel),
                Codec.INT.fieldOf("currentLevel").forGetter(ImplCharacterSheet::getCurrentLevel),
                Codec.INT.fieldOf("experience").forGetter(ImplCharacterSheet::getExperience),
                PointStorage.CODEC.fieldOf("points").forGetter(ImplCharacterSheet::getPoints),
                PathStorage.CODEC.fieldOf("paths").forGetter(ImplCharacterSheet::getPaths),
                SkillStorage.CODEC.fieldOf("skills").forGetter(ImplCharacterSheet::getSkills),
                FeatStorage.CODEC.fieldOf("feats").forGetter(ImplCharacterSheet::getFeats)
          ).apply(instance, ImplCharacterSheet::new));

  private int maxLevel = 10;
  private int currentLevel = 0;
  private int experience = 0;

  private final PointStorage points;
  private final PathStorage paths;
  private final SkillStorage skills;
  private final FeatStorage feats;

  /**
   * <p>Default Constructor</p>
   */
  public ImplCharacterSheet() {
    this.points = new PointStorage(1,0,0);
    this.paths = new PathStorage(Maps.newHashMap());
    this.skills = new SkillStorage(Maps.newHashMap());
    this.feats = new FeatStorage(Maps.newHashMap());
  }

  /**
   * <p>Codec Constructor</p>
   *
   * @param maxLevel The max level of the character.
   * @param currentLevel The current level of the character.
   * @param experience The current experience of the character.
   * @param points The serializable point storage.
   * @param paths The serializable path storage.
   * @param skills The serializable skill storage.
   * @param feats The serializable feat storage.
   */
  public ImplCharacterSheet(int maxLevel, int currentLevel, int experience, PointStorage points, PathStorage paths, SkillStorage skills, FeatStorage feats) {
    this.maxLevel = maxLevel;
    this.currentLevel = currentLevel;
    this.experience = experience;
    this.points = points;
    this.paths = paths;
    this.skills = skills;
    this.feats = feats;
  }

  @Override
  public int getMaxLevel() {
    return this.maxLevel;
  }

  @Override
  public int getCurrentLevel() {
    return this.currentLevel;
  }

  @Override
  public int getExperience() {
    return this.experience;
  }

  @Override
  public int getExperienceUntilNextLevel() {
    return 0;
  }

  @Override
  public void addExperience(int exp) {
    this.experience = Math.min(getExperience() + exp, Integer.MAX_VALUE - 1);
  }

  @Override
  public void removeExperience(int exp) {
    this.experience = Math.max(getExperience() - exp, 0);
  }

  @Override
  public void levelUp() {
    this.currentLevel = Math.min(getCurrentLevel() + 1, getMaxLevel());
  }

  @Override
  public void levelDown() {
    this.currentLevel = Math.max(getCurrentLevel() - 1, 0);
  }

  @Override
  public PointStorage getPoints() {
    return this.points;
  }

  @Override
  public PathStorage getPaths() {
    return this.paths;
  }

  @Override
  public SkillStorage getSkills() {
    return this.skills;
  }

  @Override
  public FeatStorage getFeats() {
    return this.feats;
  }
}

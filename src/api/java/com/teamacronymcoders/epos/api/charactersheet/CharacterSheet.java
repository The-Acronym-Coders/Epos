package com.teamacronymcoders.epos.api.charactersheet;

import com.teamacronymcoders.epos.api.charactersheet.storage.FeatStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.PathStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.PointStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.SkillStorage;

/**
 *
 */
public interface CharacterSheet {

  /**
   * <p>Used to get what the player's highest possible level is.</p>
   *
   * @return The player's highest possible level.
   */
  int getMaxLevel();

  /**
   * <p>Used to get what the player's current level is.</p>
   *
   * @return The player's current level.
   */
  int getCurrentLevel();

  /**
   * <p>Used to get the current amount of experience the player has.</p>
   *
   * @return The player's current amount of experience.
   */
  int getExperience();

  /**
   * <p>Used to get the currently missing experience amount until the player reaches the next level.</p>
   *
   * @return The amount of experience needed until the player reaches the next level.
   */
  int getExperienceUntilNextLevel();

  /**
   * <p>Used to increment the player's current experience count.</p>
   *
   * @param exp The amount of experience to increment
   */
  void addExperience(int exp);

  /**
   * <p>Used to decrement the player's current experience count.</p>
   *
   * @param exp The amount of experience to decrement
   */
  void removeExperience(int exp);

  /**
   * <p>Used to increment the player's current level.</p>
   */
  void levelUp();

  /**
   *  <p>Used to decrement the player's current level.</p>
   */
  void levelDown();

  /**
   * <p>
   *   Used to get the Serializable Storage object for {@link CharacterSheet} Points.
   * </p>
   *
   * @return The Serializable Storage object
   */
  PointStorage getPoints();

  /**
   * <p>
   *   Used to get the Serializable Storage object for {@link CharacterSheet} Paths.
   * </p>
   *
   * @return The Serializable Storage object
   */
  PathStorage getPaths();

  /**
   * <p>
   *   Used to get the Serializable Storage object for {@link CharacterSheet} Skills.
   * </p>
   *
   * @return The Serializable Storage object
   */
  SkillStorage getSkills();

  /**
   * <p>
   *   Used to get the Serializable Storage object for {@link CharacterSheet} Feats.
   * </p>
   *
   * @return The Serializable Storage object
   */
  FeatStorage getFeats();
}

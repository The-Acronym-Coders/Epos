package com.teamacronymcoders.epos.api.character;

import com.teamacronymcoders.epos.api.character.info.CharacterInfo;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.Paths;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

// TODO: Document Main Interface Object
public interface ICharacterSheet extends INBTSerializable<CompoundNBT> {

    /**
     * Returns the serializable {@link Paths} 's object for the Character.
     * @return Returns the {@link Paths} object.
     */
    Paths getPaths();

    /**
     * Returns the serializable {@link Skills} 's object for the Character.
     * @return Returns the {@link Skills} object.
     */
    Skills getSkills();

    /**
     * Returns the serializable {@link Feats} 's object for the Character.
     * @return Returns the {@link Feats} object.
     */
    Feats getFeats();

    /**
     * Returns the Max Character Level as an Integer.
     * @return Returns the Max Character Level.
     */
    int getMaxLevel();

    /**
     * Returns the Current Character Level as an Integer.
     * @return Returns the Current Character Level.
     */
    int getCurrentLevel();

    /**
     * Used to handle Leveling-Up the Player.
     */
    void levelUp(int levels);

    /**
     * Used to handle Leveling-Down the Player.
     */
    void levelDown(int levels);

    /**
     * Returns the current Experience total for the Character as an Integer.
     * @return Returns the current Experience Total.
     */
    int getExperience();

    /**
     * Used to handle adding Experience to the Player.
     */
    void addExperience(int experience);

    /**
     * Used to handle removing Experience to the Player.
     */
    void removeExperience(int experience);

    /**
     * Gets the {@link CharacterInfo} information object for the Player.
     * @return Returns the current {@link CharacterInfo} information object for the player
     */
    CharacterInfo getCharacterInfo();
}

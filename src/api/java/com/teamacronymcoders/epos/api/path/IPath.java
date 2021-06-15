package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.registry.IDynamic;
import net.minecraft.entity.LivingEntity;

// TODO: Document Main Interface Object
public interface IPath extends IDynamic<IPath, IPath>, IDescribable {

    /**
     * Indicates if the Path is 'Hidden' from the Player in the Path GUI.
     * @param character The {@link LivingEntity} Character.
     * @param stats The {@link LivingEntity} 's Character Stats.
     * @return Returns if the {@link IPath} is visible for the Player in the Path GUI.
     */
    boolean isHidden(LivingEntity character, ICharacterSheet stats);

    /**
     * Returns the max level of the {@link IPath} as an integer.
     * @return Returns what the 'Max' level of the {@link IPath} is.
     */
    int getMaxLevel();

    /**
     * Adds a certain amount of levels to the {@link IPath} of the {@link LivingEntity} Character.
     * @param character The {@link LivingEntity} Character.
     * @param stats The {@link LivingEntity} 's {@link ICharacterSheet}.
     * @param levelsToAdd The amount of levels to add.
     */
    void addLevel(LivingEntity character, ICharacterSheet stats, int levelsToAdd);

    /**
     * Removes a certain amount of levels to the {@link IPath} of the {@link LivingEntity} Character.
     * @param character The {@link LivingEntity} Character.
     * @param stats The {@link LivingEntity} 's {@link ICharacterSheet}.
     * @param levelsToRemove The amount of levels to remove.
     */
    void removeLevel(LivingEntity character, ICharacterSheet stats, int levelsToRemove);
}

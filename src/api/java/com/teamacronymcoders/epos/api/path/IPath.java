package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.character.ICharacterStats;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.api.registry.IDynamic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

// TODO: Document Main Interface Object
public interface IPath extends IDynamic<IPath, IPath>, IDescribable {

    /**
     * Indicates if the Path is 'Hidden' from the Player in the Path GUI.
     * @param character The {@link LivingEntity} Character.
     * @param stats The {@link LivingEntity} 's Character Stats.
     * @return Returns if the {@link IPath} is visible for the Player in the Path GUI.
     */
    boolean isHidden(LivingEntity character, ICharacterStats stats);

    /**
     * Returns the max level of the {@link IPath} as an integer.
     * @return Returns what the Max Level of the Path is.
     */
    int getMaxLevel();

    /**
     * Gets the {@link PathFeatures} storage object.
     * @return Returns the {@link PathFeatures} storage object.
     */
    PathFeatures getFeatures();

    /**
     * Adds a certain amount of levels to the {@link IPath} of the {@link LivingEntity} Character.
     * @param character The {@link LivingEntity} Character.
     * @param stats The {@link LivingEntity} 's {@link ICharacterStats}.
     * @param levelsToAdd The amount of levels to add.
     */
    void addLevel(LivingEntity character, ICharacterStats stats, int levelsToAdd);

    /**
     * Removes a certain amount of levels to the {@link IPath} of the {@link LivingEntity} Character.
     * @param character The {@link LivingEntity} Character.
     * @param stats The {@link LivingEntity} 's {@link ICharacterStats}.
     * @param levelsToRemove The amount of levels to remove.
     */
    void removeLevel(LivingEntity character, ICharacterStats stats, int levelsToRemove);
}

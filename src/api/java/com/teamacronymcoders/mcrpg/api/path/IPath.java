package com.teamacronymcoders.mcrpg.api.path;

import com.teamacronymcoders.mcrpg.api.characterstats.ICharacterStats;
import com.teamacronymcoders.mcrpg.api.pathfeature.PathFeatures;
import com.teamacronymcoders.mcrpg.api.registry.INamedRegistryEntry;
import net.minecraft.entity.LivingEntity;

public interface IPath extends INamedRegistryEntry<IPath> {
    /**
     * @return An Immutable Object Containing All the Feats, Stat Increases, and Other Features
     * that a character will receive as they level up.
     */
    PathFeatures getPathFeatures();

    void addLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel);

    void removeLevel(LivingEntity character, ICharacterStats characterStats, int newPathLevel);
}

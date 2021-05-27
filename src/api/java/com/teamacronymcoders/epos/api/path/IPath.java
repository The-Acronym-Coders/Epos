package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.path.feature.PathFeatures;
import com.teamacronymcoders.epos.api.registry.INamedRegistryEntry;
import net.minecraft.entity.LivingEntity;

public interface IPath extends INamedRegistryEntry<IPath> {
    /**
     * @return An Immutable Object Containing All the Feats, Stat Increases, and Other Features
     * that a character will receive as they level up.
     */
    PathFeatures getPathFeatures();

    void addLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel);

    void removeLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel);
}

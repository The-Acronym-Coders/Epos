package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatures;
import com.teamacronymcoders.epos.api.registry.INamedRegistryEntry;
import net.minecraft.entity.LivingEntity;

public interface IClass extends INamedRegistryEntry<IClass> {
    /**
     * @return An Immutable Object Containing All the Feats, Stat Increases, and Other Features
     * that a character will receive as they level up.
     */
    CharacterClassFeatures getClassFeatures();

    void addLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel);

    void removeLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel);
}

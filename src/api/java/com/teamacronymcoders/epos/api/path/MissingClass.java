package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatures;
import com.teamacronymcoders.epos.api.registry.MissingRegistryEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class MissingClass extends MissingRegistryEntry<IClass> implements IClass {
    public MissingClass(String registryName) {
        super(new ResourceLocation(registryName), "path");
    }

    @Override
    public CharacterClassFeatures getClassFeatures() {
        return new CharacterClassFeatures(new Int2ObjectOpenHashMap<>());
    }

    @Override
    public void addLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel) {

    }

    @Override
    public void removeLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel) {

    }
}

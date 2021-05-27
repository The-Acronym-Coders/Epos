package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.path.feature.PathFeatures;
import com.teamacronymcoders.epos.api.registry.MissingRegistryEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class MissingPath extends MissingRegistryEntry<IPath> implements IPath {
    public MissingPath(String registryName) {
        super(new ResourceLocation(registryName), "path");
    }

    @Override
    public PathFeatures getPathFeatures() {
        return new PathFeatures(new Int2ObjectOpenHashMap<>());
    }

    @Override
    public void addLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel) {

    }

    @Override
    public void removeLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel) {

    }
}

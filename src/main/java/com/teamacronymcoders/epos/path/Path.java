package com.teamacronymcoders.epos.path;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.feature.PathFeatures;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class Path implements IPath {
    private final ResourceLocation registryName;
    private final ITextComponent name;
    private final ITextComponent description;
    private final PathFeatures pathFeatures;

    public Path(ResourceLocation registryName, ITextComponent name, ITextComponent description, PathFeatures pathFeatures) {
        this.registryName = registryName;
        this.name = name;
        this.description = description;
        this.pathFeatures = pathFeatures;
    }

    public ITextComponent getName() {
        return name;
    }

    public ITextComponent getDescription() {
        return description;
    }

    @Override
    public PathFeatures getPathFeatures() {
        return pathFeatures;
    }

    @Override
    public void addLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel) {
        this.getPathFeatures().getFeaturesForLevel(newClassLevel)
            .forEach(iClassFeature -> iClassFeature.applyTo(character, characterStats));
    }

    @Override
    public void removeLevel(LivingEntity character, ICharacterStats characterStats, int newPathLevel) {
        this.getPathFeatures().getFeaturesForLevel(newPathLevel + 1)
            .forEach(iClassFeature -> iClassFeature.removeFrom(character, characterStats));
    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}

package com.teamacronymcoders.epos.classes;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.path.IClass;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatures;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class CharacterClass implements IClass {
    private final ResourceLocation registryName;
    private final ITextComponent name;
    private final ITextComponent description;
    private final CharacterClassFeatures classFeatures;

    public CharacterClass(ResourceLocation registryName, ITextComponent name, ITextComponent description, CharacterClassFeatures classFeatures) {
        this.registryName = registryName;
        this.name = name;
        this.description = description;
        this.classFeatures = classFeatures;
    }

    public ITextComponent getName() {
        return name;
    }

    public ITextComponent getDescription() {
        return description;
    }

    @Override
    public CharacterClassFeatures getClassFeatures() {
        return classFeatures;
    }

    @Override
    public void addLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel) {
        this.getClassFeatures().getFeaturesForLevel(newClassLevel)
            .forEach(iClassFeature -> iClassFeature.applyTo(character, characterStats));
    }

    @Override
    public void removeLevel(LivingEntity character, ICharacterStats characterStats, int newClassLevel) {
        this.getClassFeatures().getFeaturesForLevel(newClassLevel + 1)
            .forEach(iClassFeature -> iClassFeature.removeFrom(character, characterStats));
    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}

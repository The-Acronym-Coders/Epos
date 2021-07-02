package com.teamacronymcoders.epos.path;

import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import com.teamacronymcoders.epos.registry.PathRegistrar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path extends ForgeRegistryEntry<IPath> implements IPath {

    private final IFormattableTextComponent name;
    private final IFormattableTextComponent description;
    private final int maxLevel;
    private final PathFeatures pathFeatures;

    /**
     *
     * @param name
     * @param description
     * @param maxLevel
     */
    public Path(IFormattableTextComponent name, IFormattableTextComponent description, int maxLevel, PathFeatures pathFeatures) {
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.pathFeatures = pathFeatures;
    }

    @Override
    public IFormattableTextComponent getName() {
        return this.name;
    }

    @Override
    public IFormattableTextComponent getDescription() {
        return this.description;
    }

    @Override
    public boolean isHidden(LivingEntity character, ICharacterSheet stats) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public PathFeatures getPathFeatures() {
        return this.pathFeatures;
    }

    @Override
    public void addLevel(LivingEntity character, ICharacterSheet stats, int levelsToAdd) {
        //TODO: Implement Me
    }

    @Override
    public void removeLevel(LivingEntity character, ICharacterSheet stats, int levelsToRemove) {
        //TODO: Implement Me
    }

    @Override
    public ISerializer<? extends IPath, ?> serializer() {
        return PathRegistrar.PATH_SERIALIZER.get();
    }
}

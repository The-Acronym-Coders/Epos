package com.teamacronymcoders.epos.path;

import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.registry.PathRegistrar;
import net.ashwork.dynamicregistries.entry.DynamicEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.IFormattableTextComponent;

public class Path extends DynamicEntry<IPath> implements IPath {

    private final IFormattableTextComponent name;
    private final IFormattableTextComponent description;
    private final int maxLevel;
    private final PathFeatures pathFeatures;

    /**
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
    public ICodecEntry<? extends IPath, ?> codec() {
        return PathRegistrar.PATH_SERIALIZER.get();
    }
}

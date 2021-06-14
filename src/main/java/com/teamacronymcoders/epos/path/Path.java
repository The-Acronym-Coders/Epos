package com.teamacronymcoders.epos.path;

import com.teamacronymcoders.epos.api.character.ICharacterStats;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import com.teamacronymcoders.epos.registry.PathRegistrar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Path extends ForgeRegistryEntry<IPath> implements IPath {

    private final IFormattableTextComponent name;
    private final IFormattableTextComponent description;
    private final int maxLevel;

    // TODO: Add Path Features...
    /**
     *
     * @param name
     * @param description
     * @param maxLevel
     */
    public Path(IFormattableTextComponent name, IFormattableTextComponent description, int maxLevel) {
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
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
    public boolean isHidden(LivingEntity character, ICharacterStats stats) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public void addLevel(LivingEntity character, ICharacterStats stats, int levelsToAdd) {
        //TODO: Implement Me
    }

    @Override
    public void removeLevel(LivingEntity character, ICharacterStats stats, int levelsToRemove) {
        //TODO: Implement Me
    }

    @Override
    public ISerializer<? extends IPath, ?> serializer() {
        return PathRegistrar.PATH_SERIALIZER.get();
    }
}

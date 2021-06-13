package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.character.ICharacterStats;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PathLevels implements INBTSerializable<CompoundNBT> {
    private final Int2ObjectMap<IPath> pathLevels;
    private int currentLevel;

    public PathLevels() {
        pathLevels = new Int2ObjectOpenHashMap<>();
    }

    // TODO: Actually implement this garbage!
    public boolean levelUp(LivingEntity character, ICharacterStats stats, IPath iPath) {

        return false;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        for (Int2ObjectMap.Entry<IPath> entry : pathLevels.int2ObjectEntrySet()) {
            nbt.putString(Integer.toString(entry.getIntKey()), entry.getValue().getRegistryName().toString());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        for (String key : nbt.getAllKeys()) {
            //TODO: Fix me Ash!
            //pathLevels.put(Integer.parseInt(key), EposAPI.PATH_REGISTRY.getEntry(nbt.getString(key)));
        }
    }
}

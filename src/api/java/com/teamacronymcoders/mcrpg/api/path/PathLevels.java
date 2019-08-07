package com.teamacronymcoders.mcrpg.api.path;

import com.teamacronymcoders.mcrpg.api.MCRPGAPI;
import com.teamacronymcoders.mcrpg.api.characterstats.ICharacterStats;
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

    public boolean levelUp(LivingEntity character, ICharacterStats characterStats, IPath iPath) {
        boolean addedPath = false;
        int currentCheck = 0;
        do {
            currentCheck++;
            if (pathLevels.get(currentCheck) == null) {
                pathLevels.put(currentCheck, iPath);
                addedPath = true;
            }
        } while (!addedPath && currentCheck < currentLevel);

        int newClassTotal = 0;
        if (addedPath) {
            for (Int2ObjectMap.Entry<IPath> entry : pathLevels.int2ObjectEntrySet()) {
                if (entry.getValue() == iPath) {
                    newClassTotal++;
                }
            }
        }
        iPath.addLevel(character, characterStats, newClassTotal);
        return addedPath;
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
        for (String key : nbt.keySet()) {
            pathLevels.put(Integer.parseInt(key), MCRPGAPI.PATH_REGISTRY.getEntry(nbt.getString(key)));
        }
    }
}

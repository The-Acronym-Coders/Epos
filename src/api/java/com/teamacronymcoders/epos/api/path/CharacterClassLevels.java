package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CharacterClassLevels implements INBTSerializable<CompoundNBT> {
    private final Int2ObjectMap<IClass> classLevels;
    private int currentLevel;

    public CharacterClassLevels() {
        classLevels = new Int2ObjectOpenHashMap<>();
    }

    public boolean levelUp(LivingEntity character, ICharacterStats characterStats, IClass iPath) {
        boolean addedPath = false;
        int currentCheck = 0;
        do {
            currentCheck++;
            if (classLevels.get(currentCheck) == null) {
                classLevels.put(currentCheck, iPath);
                addedPath = true;
            }
        } while (!addedPath && currentCheck < currentLevel);

        int newClassTotal = 0;
        if (addedPath) {
            for (Int2ObjectMap.Entry<IClass> entry : classLevels.int2ObjectEntrySet()) {
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
        for (Int2ObjectMap.Entry<IClass> entry : classLevels.int2ObjectEntrySet()) {
            nbt.putString(Integer.toString(entry.getIntKey()), entry.getValue().getRegistryName().toString());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        for (String key : nbt.keySet()) {
            classLevels.put(Integer.parseInt(key), EposAPI.CLASS_REGISTRY.getEntry(nbt.getString(key)));
        }
    }
}

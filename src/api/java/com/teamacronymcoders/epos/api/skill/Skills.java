package com.teamacronymcoders.epos.api.skill;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Skills implements INBTSerializable<CompoundNBT> {
    private final Map<String, SkillInfo> skillInfoMap;

    public Skills() {
        skillInfoMap = new HashMap<>();
    }


    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}

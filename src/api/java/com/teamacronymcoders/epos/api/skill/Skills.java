package com.teamacronymcoders.epos.api.skill;

import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class Skills implements INBTSerializable<ListNBT> {

    private final Map<ResourceLocation, SkillInfo> skillInfoMap;

    public Skills() {
        skillInfoMap = new HashMap<>();
    }


    // TODO: Figure Out how the fuck to serialize this all >.>
    @Override
    public ListNBT serializeNBT() {
        return null;
    }

    // TODO: Figure Out how the fuck to deserialize this all >.>
    @Override
    public void deserializeNBT(ListNBT list) {

    }

    public static Skills createFromNBT(ListNBT nbt) {
        Skills skills = new Skills();
        skills.deserializeNBT(nbt);
        return skills;
    }
}

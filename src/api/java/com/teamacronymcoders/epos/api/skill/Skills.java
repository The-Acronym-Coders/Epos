package com.teamacronymcoders.epos.api.skill;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Skills implements INBTSerializable<CompoundNBT> {

    private final Map<ResourceLocation, SkillInfo> skillInfoMap;

    public Skills() {
        this.skillInfoMap = new HashMap<>();
    }

    @Nullable
    public SkillInfo get(ResourceLocation id) {
        return this.skillInfoMap.get(id);
    }

    //TODO: Fix this
    @Nonnull
    public SkillInfo getOrCreate(ResourceLocation id) {
        SkillInfo info = this.get(id);
        if (skillInfoMap == null) {
            //info = new SkillInfo(EposAPI.SKILL_REGISTRY.getEntryOrMissing(name));
            this.putSkillInfo(info);
        }
        return info;
    }

    public void putSkillInfo(SkillInfo skillInfo) {
        //this.skillInfoMap.putIfAbsent(skillInfo.getRegistryName(), skillInfo);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        //this.skillInfoMap.values().forEach(skillInfo -> nbt.put(skillInfo.getRegistryName(), skillInfo.serializeNBT()));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.skillInfoMap.clear();
        //nbt.getAllKeys().stream()
            //.map(key -> Pair.of(key, nbt.getCompound(key)))
            //.map(compound -> {
                //ISkill skill = EposAPI.SKILL_REGISTRY.getEntryOrMissing(compound.getKey());
                //SkillInfo skillInfo = skill.createSkillInfo();
                //skillInfo.deserializeNBT(compound.getValue());
                //return skillInfo;
            //}).forEach(skillInfo -> skillInfoMap.put(skillInfo.getRegistryName(), skillInfo));
    }

}

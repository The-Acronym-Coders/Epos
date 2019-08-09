package com.teamacronymcoders.epos.api.skill;

import com.teamacronymcoders.epos.api.EposAPI;
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

    @Nullable
    public SkillInfo get(String name) {
        return this.skillInfoMap.get(name);
    }

    @Nullable
    public SkillInfo get(ResourceLocation id) {
        return get(id.toString());
    }

    @Nullable
    public SkillInfo get(ISkill skill) {
        return this.get(skill.getRegistryName().toString());
    }

    @Nonnull
    public SkillInfo getOrCreate(ISkill skill) {
        return this.getOrCreate(skill.getRegistryName().toString());
    }

    @Nonnull
    public SkillInfo getOrCreate(String name) {
        SkillInfo skillInfo = this.get(name);
        if (skillInfo == null) {
            skillInfo = new SkillInfo(EposAPI.SKILL_REGISTRY.getEntryOrMissing(name));
            this.putSkillInfo(skillInfo);
        }
        return skillInfo;
    }

    public void putSkill(ResourceLocation registryName) {
        this.putSkillInfo(this.getOrCreate(registryName.toString()));
    }

    public void putSkill(ISkill skill) {
        this.putSkillInfo(skill.createSkillInfo());
    }

    public void putSkillInfo(SkillInfo skillInfo) {
        this.skillInfoMap.putIfAbsent(skillInfo.getRegistryName(), skillInfo);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        this.skillInfoMap.values().forEach(skillInfo -> nbt.put(skillInfo.getRegistryName(), skillInfo.serializeNBT()));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.skillInfoMap.clear();
        nbt.keySet().stream()
                .map(key -> Pair.of(key, nbt.getCompound(key)))
                .map(compound -> {
                    ISkill skill = EposAPI.SKILL_REGISTRY.getEntryOrMissing(compound.getKey());
                    SkillInfo skillInfo = skill.createSkillInfo();
                    skillInfo.deserializeNBT(compound.getValue());
                    return skillInfo;
                }).forEach(skillInfo -> skillInfoMap.put(skillInfo.getRegistryName(), skillInfo));
    }
}

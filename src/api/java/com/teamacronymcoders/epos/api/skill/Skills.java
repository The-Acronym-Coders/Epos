package com.teamacronymcoders.epos.api.skill;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

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
    public SkillInfo getSkillInfo(String skillID) {
        return this.skillInfoMap.get(new ResourceLocation(skillID));
    }

    @Nullable
    public SkillInfo getSkillInfo(ResourceLocation skillID) {
        return this.skillInfoMap.get(skillID);
    }

    @Nullable
    public SkillInfo getSkillInfo(ISkill id) {
        return this.skillInfoMap.get(id.getRegistryName());
    }

    @Nonnull
    public SkillInfo getOrCreate(String skillID) {
        SkillInfo skillInfo = this.getSkillInfo(skillID);
        if (skillInfo == null) {
            skillInfo = new SkillInfo(EposAPI.SKILL_REGISTRY.getEntryOrMissing(skillID));
            this.putSkillInfo(skillInfo);
        }
        return skillInfo;
    }

    @Nonnull
    public SkillInfo getOrCreate(ResourceLocation skillID) {
        SkillInfo skillInfo = this.getSkillInfo(skillID);
        if (skillInfo == null) {
            skillInfo = new SkillInfo(EposAPI.SKILL_REGISTRY.getEntryOrMissing(skillID));
            this.putSkillInfo(skillInfo);
        }
        return skillInfo;
    }

    @Nonnull
    public SkillInfo getOrCreate(ISkill skill) {
        SkillInfo skillInfo = this.getSkillInfo(skill);
        if (skillInfo == null) {
            skillInfo = new SkillInfo(EposAPI.SKILL_REGISTRY.getEntryOrMissing(skillInfo));
            this.putSkillInfo(skillInfo);
        }
        return skillInfo;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}

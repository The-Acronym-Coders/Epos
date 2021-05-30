package com.teamacronymcoders.epos.api.skill;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Skills implements INBTSerializable<CompoundNBT> {
    private final Map<ResourceLocation, SkillInfo> skillInfoMap;

    public Skills() {
        this.skillInfoMap = new HashMap<>();
    }

    @Nonnull
    public SkillInfo getOrCreate(ISkill skill) {
        return this.getOrCreate(skill.getRegistryName());
    }

    @Nonnull
    public SkillInfo getOrCreate(ResourceLocation skillID) {
        // TODO: Get the skill object from the Registry
        return this.skillInfoMap.computeIfAbsent(skillID, resourceLocation -> null);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {}

}
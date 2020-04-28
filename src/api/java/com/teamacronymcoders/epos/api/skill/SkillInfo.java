package com.teamacronymcoders.epos.api.skill;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public class SkillInfo implements INBTSerializable<CompoundNBT>, Comparable<SkillInfo> {
    private final String registryName;
    private final ISkill skill;
    private int experience;
    private int level;
    private boolean active;

    public SkillInfo(ISkill skill) {
        this.skill = skill;
        this.registryName = skill.toString();
    }

    @Override
    public int compareTo(SkillInfo o) {
        return 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}

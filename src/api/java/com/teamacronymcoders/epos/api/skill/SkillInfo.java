package com.teamacronymcoders.epos.api.skill;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public class SkillInfo implements INBTSerializable<CompoundNBT>, Comparable<SkillInfo> {
    private final ISkill skill;
    private int experience;
    private int level;
    private boolean active;

    public SkillInfo(ISkill skill) {
        this.skill = skill;
    }

    public ISkill getSkill() {
        return skill;
    }

    public void addExperience(int xpAmount) {
        this.setExperience(this.experience + xpAmount);
    }

    public void removeExperience(int xpAmount) {
        this.setExperience(this.experience - xpAmount);
    }

    public void setExperience(int experience) {
        this.experience = Math.max(experience, 0);
    }

    public int getExperience() {
        return experience;
    }

    public void setLevel(int level) {
        this.level = Math.max(level, 0);
    }

    public int getLevel() {
        return this.active ? level : 0;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
    }

    //TODO: Figure out a good comparability for SkillInfo
    @Override
    public int compareTo(@Nonnull SkillInfo o) {
        return 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT skillInfoNBT = new CompoundNBT();
        skillInfoNBT.putInt("skillLevel", this.getLevel());
        skillInfoNBT.putInt("skillExperience", this.getExperience());
        skillInfoNBT.putBoolean("isActive", this.getActive());
        return skillInfoNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.setLevel(nbt.getInt("skillLevel"));
        this.setExperience(nbt.getInt("skillExperience"));
        this.setActive(nbt.getBoolean("isActive"));
    }
}

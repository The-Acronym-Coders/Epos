package com.teamacronymcoders.epos.api.skill;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

//TODO: Everything that's commented out needs to be checked by Ash
public class SkillInfo implements INBTSerializable<CompoundNBT>, Comparable<SkillInfo> {
    //private final ResourceLocation registryName;
    //private final ISkill skill;
    private int experience;
    private int level;
    private boolean isActive;

    public SkillInfo(ISkill skill) {
        //this.registryName = skill.getRegistryName();
        //this.skill = skill;
    }

    //public String getRegistryName() {
    //    return this.registryName;
    //}

    //public ISkill getSkill() {
    //    return skill;
    //}

    public int getExperience() {
        return experience;
    }

    public void addExperience(int xpAmount) {
        this.setExperience(Math.max(this.getExperience() + xpAmount, Integer.MAX_VALUE)); //TODO: Have a method for checking Max Level Experience Cap!
    }

    public void setExperience(int experience) {
        this.experience = Math.min(experience, 0);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.min(level, 0);
        if (level == 0) setActive(false);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("experience", this.getExperience());
        nbt.putInt("level", this.getLevel());
        nbt.putBoolean("active", this.isActive());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.setExperience(nbt.getInt("experience"));
        this.setLevel(nbt.getInt("level"));
        this.setActive(nbt.getBoolean("active")); //&& this.skill.isFound())
    }

    @Override
    public int compareTo(SkillInfo o) {
        return 0; //this.getSkill().compareTo(o.getSkill());
    }
}

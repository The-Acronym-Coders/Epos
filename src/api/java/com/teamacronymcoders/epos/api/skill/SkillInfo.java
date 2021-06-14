package com.teamacronymcoders.epos.api.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

//TODO: Everything that's commented out needs to be checked by Ash
public class SkillInfo {

    public static final Codec<SkillInfo> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Codec.intRange(0, 256).optionalFieldOf("experience", 0).forGetter(SkillInfo::getExperience),
            Codec.intRange(0, 256).optionalFieldOf("level", 0).forGetter(SkillInfo::getLevel),
            Codec.BOOL.fieldOf("isActive").forGetter(SkillInfo::isActive))
        .apply(instance, SkillInfo::new)
    );

    private int experience;
    private int level;
    private boolean isActive;

    /**
     * Default Constructor
     */
    public SkillInfo() {
        this.experience = 0;
        this.level = 0;
        this.isActive = false;
    }

    /**
     * Codec Constructor
     *
     * @param experience
     * @param level
     * @param isActive
     */
    public SkillInfo(int experience, int level, boolean isActive) {
        this.experience = experience;
        this.level = level;
        this.isActive = isActive;
    }

    public int getExperience() {
        return experience;
    }

    // TODO: Basic implementation, Look over this later
    public void addExperience(int xpAmount) {
        this.setExperience(Math.max(this.getExperience() + xpAmount, Integer.MAX_VALUE)); //TODO: Have a method for checking Max Level Experience Cap!
    }

    // TODO: Basic implementation, Look over this later
    public void removeExperience(int xpAmount) {
        this.setExperience(Math.max(this.getExperience() - xpAmount, 0)); //TODO: Have a method for checking Max Level Experience Cap!
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

}
package com.teamacronymcoders.epos.api.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

public class SkillInfo {

    public static final Codec<SkillInfo> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    ResourceLocation.CODEC.optionalFieldOf("skillID", null).forGetter(SkillInfo::getSkillID),
                    Codec.intRange(0, 256).optionalFieldOf("maxLevel", 256).forGetter(SkillInfo::getMaxLevel),
                    Codec.intRange(0, 256).optionalFieldOf("experience", 0).forGetter(SkillInfo::getExperience),
                    Codec.intRange(0, 256).optionalFieldOf("level", 0).forGetter(SkillInfo::getLevel),
                    Codec.BOOL.fieldOf("isActive").forGetter(SkillInfo::isActive))
            .apply(instance, SkillInfo::new)
    );

    private final ResourceLocation skillID;
    private final int maxLevel;
    private int experience;
    private int level;
    private boolean isActive;

    /**
     * Default Constructor
     */
    public SkillInfo(ResourceLocation skillID) {
        this.skillID = skillID;
        this.maxLevel = 256;
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
    public SkillInfo(ResourceLocation skillID, int maxLevel, int experience, int level, boolean isActive) {
        this.skillID = skillID;
        this.maxLevel = maxLevel;
        this.experience = experience;
        this.level = level;
        this.isActive = isActive;
    }

    public ResourceLocation getSkillID() {
        return skillID;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getExperience() {
        return experience;
    }

    // TODO: Basic implementation, Look over this later
    public void addExperience(int xpAmount) {
        this.setExperience(Math.min(this.getExperience() + xpAmount, Integer.MAX_VALUE)); //TODO: Have a method for checking Max Level Experience Cap!
    }

    public void removeExperience(int xpAmount) {
        this.setExperience(Math.max(this.getExperience() - xpAmount, 0));
    }

    private void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.min(0, this.level - level);
        if (level == 0) setActive(false);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
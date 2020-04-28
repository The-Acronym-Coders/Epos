package com.teamacronymcoders.epos.api.skill;

import com.teamacronymcoders.epos.api.EposAPI;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Skill implements ISkill {

    private Int2IntOpenHashMap levelToExperienceMap;

    public Skill() {
        levelToExperienceMap = getDefaultExperienceMap(64);
    }

    @Override
    public SkillInfo createSkillInfo() {
        return new SkillInfo(this);
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public int getMinLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 16;
    }

    public void setLevelToExperienceMap(Int2IntOpenHashMap levelToExperienceMap) {
        this.levelToExperienceMap = levelToExperienceMap;
    }

    public Int2IntOpenHashMap getLevelToExperienceMap() {
        return levelToExperienceMap;
    }

    public Int2IntOpenHashMap getDefaultExperienceMap(int defaultExperience) {
        Int2IntOpenHashMap map = new Int2IntOpenHashMap();
        for (int i = 0; i < getMaxLevel(); i++) {
            if (i == 0) {
                map.put(i, defaultExperience);
                continue;
            }
            map.put(i, getExperience(defaultExperience, map.get(i-1)));
        }
        return map;
    }

    public static int getExperience(int defaultExperience, int lastLevelExperience) {
        return (int) ((defaultExperience + lastLevelExperience) * 1.25);
    }
}

package com.teamacronymcoders.epos.api.skill;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Skill extends ForgeRegistryEntry<Skill> implements ISkill {

    private Int2IntOpenHashMap levelToExperienceMap;

    public Skill() {
        levelToExperienceMap = getDefaultExperienceMap(64);
    }

    @Override
    public SkillInfo createSkillInfo() {
        return new SkillInfo(this);
    }

    public void setLevelToExperienceMap(Int2IntOpenHashMap levelToExperienceMap) {
        this.levelToExperienceMap = levelToExperienceMap;
    }

    @Override
    public Int2IntOpenHashMap getExperienceForLevelMap() {
        return levelToExperienceMap;
    }

    @Override
    public int getExperienceForLevel(int level) {
        return levelToExperienceMap.get(level);
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

    @Override
    public TranslationTextComponent getName() {
        final ResourceLocation id = this.getRegistryName();
        final String unlocalized = "skill." + id.getNamespace() + "." + id.getPath();
        return new TranslationTextComponent(unlocalized);
    }

    @Override
    public TranslationTextComponent getDescription() {
        final ResourceLocation id = this.getRegistryName();
        final String unlocalized = "skill." + id.getNamespace() + "." + id.getPath() + ".desc";
        return new TranslationTextComponent(unlocalized);
    }

    @Override
    public String toString() {
        return this.getRegistryName().toString();
    }
}

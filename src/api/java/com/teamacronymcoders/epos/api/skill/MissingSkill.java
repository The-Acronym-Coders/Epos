package com.teamacronymcoders.epos.api.skill;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class MissingSkill extends Skill {
    private final ResourceLocation registryName;

    public MissingSkill(String registryName) {
        this(new ResourceLocation(registryName));
    }

    public MissingSkill(ResourceLocation name) {
        this.registryName = name;
    }

    @Override
    public Int2IntOpenHashMap getExperienceForLevelMap() {
        return new Int2IntOpenHashMap();
    }

    @Override
    public int getExperienceForLevel(int level) {
        return 0;
    }
}

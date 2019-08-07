package com.teamacronymcoders.mcrpg.skill;

import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class Skill implements ISkill {
    private final ResourceLocation registryName;
    private final ITextComponent name;
    private final ITextComponent description;
    private final boolean hidden;
    private final int maxLevel;

    public Skill(ResourceLocation registryName, ITextComponent name, ITextComponent description,
                 boolean hidden, int maxLevel) {
        this.registryName = registryName;
        this.name = name;
        this.description = description;
        this.hidden = hidden;
        this.maxLevel = maxLevel;
    }

    @Override
    public ITextComponent getName() {
        return this.name;
    }

    @Override
    public ITextComponent getDescription() {
        return this.description;
    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    @Override
    public SkillInfo createSkillInfo() {
        return new SkillInfo(this);
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int compareTo(ISkill o) {
        return this.getName().getString().compareTo(o.getName().getString());
    }
}

package com.teamacronymcoders.epos.api.skill;

import javax.annotation.Nonnull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MissingSkill implements ISkill {
    private final ResourceLocation registryName;

    public MissingSkill(String registryName) {
        this(new ResourceLocation(registryName));
    }

    public MissingSkill(ResourceLocation name) {
        this.registryName = name;
    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public boolean isFound() {
        return false;
    }

    @Override
    public ITextComponent getName() {
        return null;
    }

    @Override
    public ITextComponent getDescription() {
        return null;
    }

    @Override
    public int compareTo(ISkill o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getName().getString(), o.getName().getString());
    }

    @Override
    public SkillInfo createSkillInfo() {
        return new SkillInfo(this);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }
}

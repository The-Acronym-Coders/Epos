package com.teamacronymcoders.epos.api.skill;

import net.minecraft.util.ResourceLocation;

public class MissingSkill extends Skill {
    private final ResourceLocation registryName;

    public MissingSkill(String registryName) {
        this(new ResourceLocation(registryName));
    }

    public MissingSkill(ResourceLocation name) {
        this.registryName = name;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

}

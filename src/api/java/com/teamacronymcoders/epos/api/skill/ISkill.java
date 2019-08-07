package com.teamacronymcoders.epos.api.skill;

import com.teamacronymcoders.epos.api.registry.INamedRegistryEntry;

public interface ISkill extends INamedRegistryEntry<ISkill> {
    SkillInfo createSkillInfo();

    boolean isHidden();

    int getMaxLevel();
}

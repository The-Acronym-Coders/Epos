package com.teamacronymcoders.mcrpg.api.skill;

import com.teamacronymcoders.mcrpg.api.registry.INamedRegistryEntry;

public interface ISkill extends INamedRegistryEntry<ISkill> {
    SkillInfo createSkillInfo();

    boolean isHidden();

    int getMaxLevel();
}

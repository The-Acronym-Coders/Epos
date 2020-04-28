package com.teamacronymcoders.epos.api.skill;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public interface ISkill {
    SkillInfo createSkillInfo();

    boolean isHidden();

    default int getMinLevel(){
        return 0;
    }
    default int getMaxLevel() {
        return 16;
    }

    Int2IntOpenHashMap getExperienceForLevelMap();
    int getExperienceForLevel(int level);
}

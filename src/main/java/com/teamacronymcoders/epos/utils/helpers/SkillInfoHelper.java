package com.teamacronymcoders.epos.utils.helpers;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import net.minecraft.util.ResourceLocation;

public class SkillInfoHelper {
    public static int getSkillLevel(ResourceLocation id, ICharacterStats stats) {
        SkillInfo info = stats.getSkills().get(id.toString());
        return info != null ? info.getLevel() : 0;
    }
}

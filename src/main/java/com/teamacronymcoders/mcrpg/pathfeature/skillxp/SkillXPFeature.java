package com.teamacronymcoders.mcrpg.pathfeature.skillxp;

import com.teamacronymcoders.mcrpg.api.characterstats.ICharacterStats;
import com.teamacronymcoders.mcrpg.api.skill.ISkill;
import com.teamacronymcoders.mcrpg.pathfeature.PathFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillXPFeature extends PathFeature {
    private final ISkill skill;
    private final int xpAmount;

    public SkillXPFeature(ISkill skill, int xpAmount) {
        super(new TranslationTextComponent("pathfeature.mcrpg.skillxp.name", skill.getName(), xpAmount),
            new TranslationTextComponent("pathfeature.mcrpg.skillxp.description", skill.getName(), xpAmount));
        this.skill = skill;
        this.xpAmount = xpAmount;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterStats characterStats) {
        characterStats.getSkills().getOrCreate(skill).addExperience(xpAmount);
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterStats characterStats) {
        characterStats.getSkills().getOrCreate(skill).addExperience(-xpAmount);
    }
}

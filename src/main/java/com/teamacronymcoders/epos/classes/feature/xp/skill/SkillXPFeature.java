package com.teamacronymcoders.epos.classes.feature.xp.skill;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.classes.feature.CharacterClassFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillXPFeature extends CharacterClassFeature {
    private final ISkill skill;
    private final int xpAmount;

    public SkillXPFeature(ISkill skill, int xpAmount) {
        super(new TranslationTextComponent("pathfeature.epos.skillxp.name", skill.getName(), xpAmount),
              new TranslationTextComponent("pathfeature.epos.skillxp.description", skill.getName(), xpAmount));
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

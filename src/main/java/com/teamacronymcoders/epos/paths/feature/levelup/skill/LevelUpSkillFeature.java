package com.teamacronymcoders.epos.classes.feature.levelup.skill;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.classes.feature.CharacterClassFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class LevelUpSkillFeature extends CharacterClassFeature {
    private final ISkill skill;
    private final int levels;

    public LevelUpSkillFeature(ISkill skill, int levels) {
        super(new TranslationTextComponent("pathfeature.epos.levelup.name", levels, skill.getName()),
              new TranslationTextComponent("pathfeature.epos.levelup.description", levels, skill.getName()));
        this.skill = skill;
        this.levels = levels;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterStats characterStats) {
        int level = characterStats.getSkills().getOrCreate(skill).getLevel();
        SkillInfo info = characterStats.getSkills().get(skill);
        if (info != null) {
            info.setLevel(level + levels);
        }
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterStats characterStats) {
        int level = characterStats.getSkills().getOrCreate(skill).getLevel();
        SkillInfo info = characterStats.getSkills().get(skill);
        if (info != null) {
            info.setLevel(level + levels);
        }
    }
}

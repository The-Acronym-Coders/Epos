package com.teamacronymcoders.eposmajorum.pathfeature.levelupskill;

import com.teamacronymcoders.eposmajorum.api.characterstats.ICharacterStats;
import com.teamacronymcoders.eposmajorum.api.skill.ISkill;
import com.teamacronymcoders.eposmajorum.pathfeature.PathFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class LevelUpSkillFeature extends PathFeature {
    private final ISkill skill;
    private final int levels;

    public LevelUpSkillFeature(ISkill skill, int levels) {
        super(new TranslationTextComponent("pathfeature.eposmajorum.levelup.name", skill.getName()),
              new TranslationTextComponent("pathfeature.eposmajorum.levelup.description", skill.getName()));
        this.skill = skill;
        this.levels = levels;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterStats characterStats) {
        int level = characterStats.getSkills().getOrCreate(skill).getLevel();
        characterStats.getSkills().getOrCreate(skill).setLevel(level + levels);
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterStats characterStats) {
        int level = characterStats.getSkills().getOrCreate(skill).getLevel();
        characterStats.getSkills().getOrCreate(skill).setLevel(level - levels);
    }
}

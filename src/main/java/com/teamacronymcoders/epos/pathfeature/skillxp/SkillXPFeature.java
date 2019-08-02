package com.teamacronymcoders.epos.pathfeature.skillxp;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.pathfeature.PathFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillXPFeature extends PathFeature {
    private final ISkill skill;
    private final int xpAmount;

    public SkillXPFeature(ISkill skill, int xpAmount) {
<<<<<<< cb35bacfea2792459db9e29d3a9eaac40d952883:src/main/java/com/teamacronymcoders/epos/pathfeature/skillxp/SkillXPFeature.java
        super(new TranslationTextComponent("pathfeature.epos.skillxp.name", skill.getName(), xpAmount),
            new TranslationTextComponent("pathfeature.epos.skillxp.description", skill.getName(), xpAmount));
=======
        super(new TranslationTextComponent("pathfeature.eposmajorum.skillxp.name", xpAmount, skill.getName()),
<<<<<<< cb35bacfea2792459db9e29d3a9eaac40d952883:src/main/java/com/teamacronymcoders/epos/pathfeature/skillxp/SkillXPFeature.java
            new TranslationTextComponent("pathfeature.eposmajorum.skillxp.description", xpAmount, skill.getName()));
>>>>>>> PathFeature stuff:src/main/java/com/teamacronymcoders/eposmajorum/pathfeature/skillxp/SkillXPFeature.java
=======
                new TranslationTextComponent("pathfeature.eposmajorum.skillxp.description", xpAmount, skill.getName()));
>>>>>>> Reformat Code and Optimize Imports:src/main/java/com/teamacronymcoders/eposmajorum/pathfeature/skillxp/SkillXPFeature.java
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

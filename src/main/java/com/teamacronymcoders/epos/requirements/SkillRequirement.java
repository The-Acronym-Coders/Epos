package com.teamacronymcoders.epos.requirements;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.RequirementComparision;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public class SkillRequirement implements IRequirement {

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Implement
        return null;
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        //TODO: Implement
        return false;
    }

    @Nonnull
    @Override
    public RequirementComparision compare(IRequirement other) {
        if (other == this) {
            return RequirementComparision.IDENTICAL;
        } else if (other instanceof SkillRequirement) {
            //TODO: Check the skill information of the two requirements to try and give a better comparision
            return RequirementComparision.TYPE_MATCHES;
        }
        return RequirementComparision.INCOMPATIBLE;
    }
}
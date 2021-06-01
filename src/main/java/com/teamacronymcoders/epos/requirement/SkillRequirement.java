package com.teamacronymcoders.epos.requirement;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
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
    public RequirementComparison compare(IRequirement other) {
        if (other == this) {
            return RequirementComparison.IDENTICAL;
        } else if (other instanceof SkillRequirement) {
            //TODO: Check the skill information of the two requirements to try and give a better comparison
            return RequirementComparison.TYPE_MATCHES;
        }
        return RequirementComparison.INCOMPATIBLE;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return other instanceof SkillRequirement;
    }
}
package com.teamacronymcoders.epos.requirements.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.RequirementComparision;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public class NOTRequirement implements IRequirement {

    @Nonnull
    private final IRequirement requirement;

    public NOTRequirement(@Nonnull IRequirement requirement) {
        this.requirement = requirement;
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        return requirement.getToolTip(!matches);
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return !requirement.isMet(entity, stats);
    }

    @Nonnull
    @Override
    public RequirementComparision compare(IRequirement other) {
        if (other == this) {
            return RequirementComparision.IDENTICAL;
        } else if (other instanceof NOTRequirement) {
            RequirementComparision innerComparision = requirement.compare(((NOTRequirement) other).requirement);
            if (innerComparision == RequirementComparision.IDENTICAL) {
                return RequirementComparision.IDENTICAL;
            } else if (innerComparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {
                return RequirementComparision.LESS_RESTRICTIVE_THAN;
            } else if (innerComparision == RequirementComparision.LESS_RESTRICTIVE_THAN) {
                return RequirementComparision.MORE_RESTRICTIVE_THAN;
            }
            //TODO: Evaluate if we should return incompatible if the sub types are incompatible but they are both NOT requirements
            return RequirementComparision.TYPE_MATCHES;
        }
        return RequirementComparision.INCOMPATIBLE;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return other instanceof NOTRequirement;
    }
}
package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
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
    public RequirementComparison compare(IRequirement other) {
        if (other == this) {
            return RequirementComparison.IDENTICAL;
        } else if (other instanceof NOTRequirement) {
            RequirementComparison innerComparison = requirement.compare(((NOTRequirement) other).requirement);
            if (innerComparison == RequirementComparison.IDENTICAL) {
                return RequirementComparison.IDENTICAL;
            } else if (innerComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
                return RequirementComparison.LESS_RESTRICTIVE_THAN;
            } else if (innerComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
                return RequirementComparison.MORE_RESTRICTIVE_THAN;
            }
            //TODO: Evaluate if we should return incompatible if the sub types are incompatible but they are both NOT requirements
            return RequirementComparison.TYPE_MATCHES;
        }
        return RequirementComparison.INCOMPATIBLE;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return other instanceof NOTRequirement;
    }
}
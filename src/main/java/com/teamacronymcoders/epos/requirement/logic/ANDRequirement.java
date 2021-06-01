package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ANDRequirement extends DoubleRequirement {

    public ANDRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        super(leftRequirement, rightRequirement);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file with the proper format, and matching values, as one may match and the other may not
        return new TranslationTextComponent("requirement.epos.tooltip.and", leftRequirement.getToolTip(matches), rightRequirement.getToolTip(matches));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return leftRequirement.isMet(entity, stats) && rightRequirement.isMet(entity, stats);
    }

    @Override
    protected boolean isInstance(DoubleRequirement requirement) {
        return requirement instanceof ANDRequirement;
    }

    @Nullable
    @Override
    protected RequirementComparison getComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        //TODO: Should this upper logic be moved to DoubleRequirement?
        boolean leftIdentical = leftComparison == RequirementComparison.IDENTICAL;
        boolean rightIdentical = rightComparison == RequirementComparison.IDENTICAL;
        if (leftIdentical && (rightIdentical || rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN || rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN)) {
            //If one of the requirements are identical then we can take the value of the other one
            return rightComparison;
        } else if (rightIdentical && (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN || leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN)) {
            //If one of the requirements are identical then we can take the value of the other one
            return leftComparison;
        }
        //AND specific logic
        else if (leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        } else if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN && rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
            return RequirementComparison.LESS_RESTRICTIVE_THAN;
        }
        return null;
    }

    @Nullable
    @Override
    protected RequirementComparison getSingleComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        if (leftComparison == rightComparison) {
            //If they both agree on the comparison type, then we go with that type
            return leftComparison;
        } else if (leftComparison == RequirementComparison.IDENTICAL && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN ||
                   rightComparison == RequirementComparison.IDENTICAL && leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
            //If one is identical, but the other is more restrictive, then we are more restrictive overall
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        } else if (leftComparison == RequirementComparison.IDENTICAL && rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN ||
                   rightComparison == RequirementComparison.IDENTICAL && leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
            //If one is identical, but the other is less restrictive, then the most restrictive comparison is identical
            return RequirementComparison.IDENTICAL;
        } else if (leftComparison == RequirementComparison.IDENTICAL || rightComparison == RequirementComparison.IDENTICAL) {
            //If only one of them is identical, we have extra restrictions being the second requirement so we are more restrictive
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        } else if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN ||
                   rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN && leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
            //If one is more restrictive, but the other is less restrictive, then they are incompatible
            return RequirementComparison.INCOMPATIBLE;
        }
        return null;
    }
}
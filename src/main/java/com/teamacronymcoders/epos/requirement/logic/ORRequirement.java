package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ORRequirement extends DoubleRequirement {

    public ORRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        super(leftRequirement, rightRequirement);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file with the proper format, and matching values, as one may match and the other may not
        return new TranslationTextComponent("requirement.epos.tooltip.or", leftRequirement.getToolTip(matches), rightRequirement.getToolTip(matches));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return leftRequirement.isMet(entity, stats) || rightRequirement.isMet(entity, stats);
    }

    @Override
    protected boolean isInstance(DoubleRequirement requirement) {
        return requirement instanceof ORRequirement;
    }

    @Nullable
    @Override
    protected RequirementComparison getComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        //If one of the requirements are identical then we can take the value of the other one
        if (leftComparison == RequirementComparison.IDENTICAL && (rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN ||
                                                                  rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN)) {
            return rightComparison;
        } else if (rightComparison == RequirementComparison.IDENTICAL && (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN ||
                                                                          leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN)) {
            return leftComparison;
        } else if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN && rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
            //If both parts of the other requirement are less restrictive than our counterparts the other requirement is less restrictive than us
            return RequirementComparison.LESS_RESTRICTIVE_THAN;
        } else if (leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
            //If both parts of the other requirement are more restrictive than our counterparts the other requirement is more restrictive than us
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        }
        //One is less restrictive and one is more restrictive means we don't have any simplification that can take place
        return null;
    }

    @Nullable
    @Override
    protected RequirementComparison getSingleComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        if (leftComparison == rightComparison && leftComparison != RequirementComparison.TYPE_MATCHES) {
            //If they both agree on the comparison type, then we go with that type if it is one of the types that is comparison based
            // Though skip it if it is just a type matches as we don't actually match the type
            return leftComparison;
        } else if (leftComparison == RequirementComparison.IDENTICAL && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN ||
                   rightComparison == RequirementComparison.IDENTICAL && leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
            //If one is identical, but the requirement we are comparing against is more restrictive than our other requirement
            // then the requirement we are testing is more restrictive than our OR requirement as we have extra valid cases
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        } else if (leftComparison == RequirementComparison.IDENTICAL && rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN ||
                   rightComparison == RequirementComparison.IDENTICAL && leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
            //If one is identical, but the requirement we are comparing against is less restrictive than our other requirement
            // then the requirement we are testing is as restrictive as our OR requirement
            return RequirementComparison.IDENTICAL;
        } else if (leftComparison == RequirementComparison.IDENTICAL || rightComparison == RequirementComparison.IDENTICAL) {
            //If only one of them is identical, the requirement we are testing is more restrictive than our OR requirement
            // as we have an extra cases that are valid
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        }
        return null;
    }

    @Nonnull
    @Override
    public IRequirement simplify() {
        if (leftRequirement == TrueRequirement.INSTANCE || rightRequirement == TrueRequirement.INSTANCE) {
            return TrueRequirement.INSTANCE;
        }
        return super.simplify();
    }

    @Nullable
    @Override
    protected IRequirement simplify(@Nonnull IRequirement a, @Nonnull IRequirement b) {
        if (a.canCompareWith(b)) {
            RequirementComparison comparison = a.compare(b);
            if (comparison == RequirementComparison.IDENTICAL) {
                //If the two requirements have the same restrictions, pick the simpler to represent one of the two
                return pickSimplest(a, b);
            } else if (comparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
                //If the second requirement is more restrictive than the first one go with the first one
                return a;
            } else if (comparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
                //If the second requirement is less restrictive than the first one go with it
                return b;
            } else if (comparison == RequirementComparison.OPPOSITE) {
                return TrueRequirement.INSTANCE;
            }
        }
        return null;
    }
}
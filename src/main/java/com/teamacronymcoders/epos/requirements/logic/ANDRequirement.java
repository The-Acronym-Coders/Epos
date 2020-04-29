package com.teamacronymcoders.epos.requirements.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.RequirementComparision;
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
    protected RequirementComparision getComparision(RequirementComparision leftComparision, RequirementComparision rightComparision) {
        //TODO: Should this upper logic be moved to DoubleRequirement?
        boolean leftIdentical = leftComparision == RequirementComparision.IDENTICAL;
        boolean rightIdentical = rightComparision == RequirementComparision.IDENTICAL;
        if (leftIdentical && (rightIdentical || rightComparision == RequirementComparision.LESS_RESTRICTIVE_THAN || rightComparision == RequirementComparision.MORE_RESTRICTIVE_THAN)) {
            //If one of the requirements are identical then we can take the value of the other one
            return rightComparision;
        } else if (rightIdentical && (leftComparision == RequirementComparision.LESS_RESTRICTIVE_THAN || leftComparision == RequirementComparision.MORE_RESTRICTIVE_THAN)) {
            //If one of the requirements are identical then we can take the value of the other one
            return leftComparision;
        }
        //AND specific logic
        else if (leftComparision == RequirementComparision.MORE_RESTRICTIVE_THAN && rightComparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {
            return RequirementComparision.MORE_RESTRICTIVE_THAN;
        } else if (leftComparision == RequirementComparision.LESS_RESTRICTIVE_THAN && rightComparision == RequirementComparision.LESS_RESTRICTIVE_THAN) {
            return RequirementComparision.LESS_RESTRICTIVE_THAN;
        }
        return null;
    }

    @Nullable
    @Override
    protected RequirementComparision getSingleComparision(RequirementComparision leftComparision, RequirementComparision rightComparision) {
        if (leftComparision == rightComparision) {
            //If they both agree on the comparision type, then we go with that type
            return leftComparision;
        } else if (leftComparision == RequirementComparision.IDENTICAL && rightComparision == RequirementComparision.MORE_RESTRICTIVE_THAN ||
                   rightComparision == RequirementComparision.IDENTICAL && leftComparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {
            //If one is identical, but the other is more restrictive, then we are more restrictive overall
            return RequirementComparision.MORE_RESTRICTIVE_THAN;
        } else if (leftComparision == RequirementComparision.IDENTICAL && rightComparision == RequirementComparision.LESS_RESTRICTIVE_THAN ||
                   rightComparision == RequirementComparision.IDENTICAL && leftComparision == RequirementComparision.LESS_RESTRICTIVE_THAN) {
            //If one is identical, but the other is less restrictive, then the most restrictive comparision is identical
            return RequirementComparision.IDENTICAL;
        } else if (leftComparision == RequirementComparision.IDENTICAL || rightComparision == RequirementComparision.IDENTICAL) {
            //If only one of them is identical, we have extra restrictions being the second requirement so we are more restrictive
            return RequirementComparision.MORE_RESTRICTIVE_THAN;
        } else if (leftComparision == RequirementComparision.LESS_RESTRICTIVE_THAN && rightComparision == RequirementComparision.MORE_RESTRICTIVE_THAN ||
                   rightComparision == RequirementComparision.LESS_RESTRICTIVE_THAN && leftComparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {
            //If one is more restrictive, but the other is less restrictive, then they are incompatible
            return RequirementComparision.INCOMPATIBLE;
        }
        return null;
    }
}
package com.teamacronymcoders.epos.requirements.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.RequirementComparision;
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
        //TODO: Add the lang key to the lang file with the proper format
        return new TranslationTextComponent("requirement.epos.tooltip.or", leftRequirement.getToolTip(!matches), rightRequirement.getToolTip(!matches));
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
    protected RequirementComparision getComparision(RequirementComparision leftComparision, RequirementComparision rightComparision) {
        boolean leftIdentical = leftComparision == RequirementComparision.IDENTICAL;
        boolean rightIdentical = rightComparision == RequirementComparision.IDENTICAL;
        if (leftIdentical && (rightIdentical || rightComparision == RequirementComparision.LESS_RESTRICTIVE_THAN || rightComparision == RequirementComparision.MORE_RESTRICTIVE_THAN)) {
            //If one of the requirements are identical then we can take the value of the other one
            return rightComparision;
        } else if (rightIdentical && (leftComparision == RequirementComparision.LESS_RESTRICTIVE_THAN || leftComparision == RequirementComparision.MORE_RESTRICTIVE_THAN)) {
            //If one of the requirements are identical then we can take the value of the other one
            return leftComparision;
        }
        //Or specific logic
        //TODO: Implement
        else if (leftComparision == RequirementComparision.LESS_RESTRICTIVE_THAN && rightComparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {

        } else if (leftComparision == RequirementComparision.LESS_RESTRICTIVE_THAN && rightComparision == RequirementComparision.LESS_RESTRICTIVE_THAN) {

        } else if (leftComparision == RequirementComparision.MORE_RESTRICTIVE_THAN && rightComparision == RequirementComparision.LESS_RESTRICTIVE_THAN) {

        } else if (leftComparision == RequirementComparision.MORE_RESTRICTIVE_THAN && rightComparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {

        }
        return null;
    }
}
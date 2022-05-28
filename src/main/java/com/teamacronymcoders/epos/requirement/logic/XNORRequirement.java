package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class XNORRequirement extends DoubleRequirement {

    public XNORRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        super(leftRequirement, rightRequirement);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file with the proper format, and matching values, as one may match and the other may not
        return new TranslationTextComponent("requirement.epos.tooltip.xnor", leftRequirement.getToolTip(matches), rightRequirement.getToolTip(matches));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return leftRequirement.isMet(entity, stats) == rightRequirement.isMet(entity, stats);
    }

    @Override
    protected boolean isInstance(DoubleRequirement requirement) {
        return requirement instanceof XNORRequirement;
    }

    @Nullable
    @Override
    protected RequirementComparison getComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        //TODO: Implement
        // T T | T
        // T F | F
        // F T | F
        // F F | T
        if (leftComparison == RequirementComparison.IDENTICAL) {
            if (rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {

            } else if (rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {

            }
        } else if (rightComparison == RequirementComparison.IDENTICAL) {
            if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {

            } else if (leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {

            }
        } else if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN && rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {

        } else if (leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {

        } else if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {

        } else if (leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN && rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {

        }
        return null;
    }

    @Nullable
    @Override
    protected RequirementComparison getSingleComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        //I don't believe there are any cases where XNOR can be compared against a single element
        return null;
    }

    @Nullable
    @Override
    protected IRequirement simplify(@Nonnull IRequirement a, @Nonnull IRequirement b) {
        if (a.canCompareWith(b)) {
            RequirementComparison comparison = a.compare(b);
            if (comparison == RequirementComparison.IDENTICAL) {
                return TrueRequirement.INSTANCE;
            } else if (comparison == RequirementComparison.OPPOSITE) {
                return FalseRequirement.INSTANCE;
            }
        }
        return null;
    }
}
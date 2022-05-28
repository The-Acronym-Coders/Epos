package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class XORRequirement extends DoubleRequirement {

    public XORRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        super(leftRequirement, rightRequirement);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file with the proper format, and matching values, as one may match and the other may not
        return new TranslationTextComponent("requirement.epos.tooltip.xor", leftRequirement.getToolTip(matches), rightRequirement.getToolTip(matches));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return leftRequirement.isMet(entity, stats) != rightRequirement.isMet(entity, stats);
    }

    @Override
    protected boolean isInstance(DoubleRequirement requirement) {
        return requirement instanceof XORRequirement;
    }

    @Nullable
    @Override
    protected RequirementComparison getComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        //TODO: Implement
        // T T | F
        // T F | T
        // F T | T
        // F F | F
        // a xor b
        // c xor d
        if (leftComparison == RequirementComparison.IDENTICAL) {
            if (rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
                //TODO
                // B < 2
                // C < 3
                // A XNOR B
                // A XNOR C
                // A => TRUE
                // B => TRUE -> TRUE XNOR TRUE ?= TRUE XNOR C -> FALSE ?= TRUE XNOR C -> ==
                // B => FALSE -> TRUE XNOR FALSE ?= TRUE XNOR C -> TRUE ?= TRUE XNOR C -> ??
                // A => FALSE
                // B => TRUE -> FALSE XNOR TRUE ?= FALSE XNOR C -> TRUE ?= TRUE XNOR C -> ??
                // B => FALSE -> FALSE XNOR FALSE ?= FALSE XNOR C -> FALSE ?= TRUE XNOR C -> ==
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
        //I don't believe there are any cases where XOR can be compared against a single element
        return null;
    }

    @Nonnull
    @Override
    public IRequirement simplify() {
        if (leftRequirement == FalseRequirement.INSTANCE && rightRequirement != FalseRequirement.INSTANCE ||
            leftRequirement != FalseRequirement.INSTANCE && rightRequirement == FalseRequirement.INSTANCE) {
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
                return FalseRequirement.INSTANCE;
            } else if (comparison == RequirementComparison.OPPOSITE) {
                return TrueRequirement.INSTANCE;
            }
        }
        return null;
    }
}
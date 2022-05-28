package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class NORRequirement extends DoubleRequirement {

    public NORRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        super(leftRequirement, rightRequirement);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file with the proper format, and matching values, as one may match and the other may not
        return new TranslationTextComponent("requirement.epos.tooltip.nor", leftRequirement.getToolTip(matches), rightRequirement.getToolTip(matches));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return !leftRequirement.isMet(entity, stats) && !rightRequirement.isMet(entity, stats);
    }

    @Override
    protected boolean isInstance(DoubleRequirement requirement) {
        return requirement instanceof NORRequirement;
    }

    @Nullable
    @Override
    protected RequirementComparison getComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        //If one of the requirements are identical then we can take the inverse of the other one as NOR goes to true when both are false
        // so one being the same means we are effectively comparing the restrictiveness of the other one going to false
        if (leftComparison == RequirementComparison.IDENTICAL) {
            if (rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
                return RequirementComparison.MORE_RESTRICTIVE_THAN;
            } else if (rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
                return RequirementComparison.LESS_RESTRICTIVE_THAN;
            }
        } else if (rightComparison == RequirementComparison.IDENTICAL) {
            if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
                return RequirementComparison.MORE_RESTRICTIVE_THAN;
            } else if (leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
                return RequirementComparison.LESS_RESTRICTIVE_THAN;
            }
        } else if (leftComparison == RequirementComparison.LESS_RESTRICTIVE_THAN && rightComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
            //If both are less restrictive then overall it is more restrictive as the likely hood of them both converging on false is higher
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        } else if (leftComparison == RequirementComparison.MORE_RESTRICTIVE_THAN && rightComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
            //If both are more restrictive then overall it is less restrictive as the likely hood of them both converging on false is lower
            return RequirementComparison.LESS_RESTRICTIVE_THAN;
        }
        //One is less restrictive and one is more restrictive means we don't have any simplification that can take place
        return null;
    }

    @Nullable
    @Override
    protected RequirementComparison getSingleComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        if (leftComparison == RequirementComparison.IDENTICAL && rightComparison == RequirementComparison.IDENTICAL) {
            //A NOR A ?= A
            //A => TRUE -> TRUE NOR TRUE = FALSE = !TRUE
            //A => FALSE -> FALSE NOR FALSE = TRUE = !FALSE
            //A NOR A = !A
            return RequirementComparison.OPPOSITE;
        } else if (leftComparison == RequirementComparison.OPPOSITE && rightComparison == RequirementComparison.OPPOSITE) {
            //A NOR A ?= !A
            //A => TRUE -> TRUE NOR TRUE = FALSE = !TRUE
            //A => FALSE -> FALSE NOR FALSE = TRUE = !FALSE
            //A NOR A = !A
            return RequirementComparison.IDENTICAL;
        }
        return null;
    }

    @Nonnull
    @Override
    public IRequirement simplify() {
        if (leftRequirement == TrueRequirement.INSTANCE || rightRequirement == TrueRequirement.INSTANCE) {
            return FalseRequirement.INSTANCE;
        }
        return super.simplify();
    }

    @Nullable
    @Override
    protected IRequirement simplify(@Nonnull IRequirement a, @Nonnull IRequirement b) {
        if (a.canCompareWith(b)) {
            RequirementComparison comparison = a.compare(b);
            if (comparison == RequirementComparison.IDENTICAL) {
                return NOTRequirement.simplifiedNot(pickSimplest(a, b));
            } else if (comparison == RequirementComparison.OPPOSITE) {
                return FalseRequirement.INSTANCE;
            }
        }
        return null;
    }
}
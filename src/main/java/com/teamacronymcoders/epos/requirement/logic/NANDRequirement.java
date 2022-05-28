package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class NANDRequirement extends DoubleRequirement {

    public NANDRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        super(leftRequirement, rightRequirement);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file with the proper format, and matching values, as one may match and the other may not
        return new TranslationTextComponent("requirement.epos.tooltip.nand", leftRequirement.getToolTip(matches), rightRequirement.getToolTip(matches));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return !leftRequirement.isMet(entity, stats) || !rightRequirement.isMet(entity, stats);
    }

    @Override
    protected boolean isInstance(DoubleRequirement requirement) {
        return requirement instanceof NANDRequirement;
    }

    @Nullable
    @Override
    protected RequirementComparison getComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        //I don't believe there are any cases (except for the parent identical one) where two NAND gates can be compared
        return null;
    }

    @Nullable
    @Override
    protected RequirementComparison getSingleComparison(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        if (leftComparison == RequirementComparison.IDENTICAL && rightComparison == RequirementComparison.IDENTICAL) {
            //A NAND A ?= A
            //A => TRUE -> TRUE NAND TRUE = FALSE = !TRUE
            //A => FALSE -> FALSE NAND FALSE = TRUE = !FALSE
            //A NAND A = !A
            return RequirementComparison.OPPOSITE;
        } else if (leftComparison == RequirementComparison.OPPOSITE && rightComparison == RequirementComparison.OPPOSITE) {
            //A NAND A ?= !A
            //A => TRUE -> TRUE NAND TRUE = FALSE = !TRUE
            //A => FALSE -> FALSE NAND FALSE = TRUE = !FALSE
            //A NAND A = !A
            return RequirementComparison.IDENTICAL;
        }
        return null;
    }

    @Nonnull
    @Override
    public IRequirement simplify() {
        if (leftRequirement == FalseRequirement.INSTANCE || rightRequirement == FalseRequirement.INSTANCE) {
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
                return NOTRequirement.simplifiedNot(pickSimplest(a, b));
            } else if (comparison == RequirementComparison.OPPOSITE) {
                return TrueRequirement.INSTANCE;
            }
        }
        return null;
    }
}
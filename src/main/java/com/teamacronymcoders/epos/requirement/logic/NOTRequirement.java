package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public class NOTRequirement implements ILogicRequirement {

    public static IRequirement simplifiedNot(@Nonnull IRequirement requirement) {
        //TODO: Should we have not requirements get simplified on creation when done through code (or have that handled manually)
        // for example in NOR requirement where we create one of these
        return simplifyNot(null, requirement);
    }

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
            //TODO: Do we want to check canCompare here (it isn't strictly needed but may be worthwhile)
            RequirementComparison innerComparison = requirement.compare(((NOTRequirement) other).requirement);
            if (innerComparison == RequirementComparison.IDENTICAL) {
                return RequirementComparison.IDENTICAL;
            } else if (innerComparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
                return RequirementComparison.LESS_RESTRICTIVE_THAN;
            } else if (innerComparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
                return RequirementComparison.MORE_RESTRICTIVE_THAN;
            } else if (innerComparison == RequirementComparison.OPPOSITE) {
                return RequirementComparison.OPPOSITE;
            }
            //TODO: Evaluate if we should return incompatible if the sub types are incompatible but they are both NOT requirements
            return RequirementComparison.TYPE_MATCHES;
        } else if (requirement.canCompareWith(other)) {
            RequirementComparison innerComparison = requirement.compare(other);
            if (innerComparison == RequirementComparison.IDENTICAL) {
                return RequirementComparison.OPPOSITE;
            } else if (innerComparison == RequirementComparison.OPPOSITE) {
                return RequirementComparison.IDENTICAL;
            }//TODO: Are there any other cases (also should we somehow have this check the other way around in case the other one can compare with our inner one
        }
        return RequirementComparison.INCOMPATIBLE;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return other instanceof NOTRequirement || requirement.canCompareWith(other);
    }

    @Nonnull
    @Override
    public IRequirement simplify() {
        return simplifyNot(this, requirement);
    }

    @Nonnull
    private static IRequirement simplifyNot(@Nullable IRequirement thisRequirement, @Nonnull IRequirement innerRequirement) {
        if (innerRequirement instanceof NOTRequirement) {
            //TODO: Should this be requirement.requirement.simplify()
            // or not because of "always" having the inner ones be simplified
            return ((NOTRequirement) innerRequirement).requirement;
        } else if (innerRequirement == FalseRequirement.INSTANCE) {
            return TrueRequirement.INSTANCE;
        } else if (innerRequirement == TrueRequirement.INSTANCE) {
            return FalseRequirement.INSTANCE;
        }
        //TODO: Should we have any special casing for NOT|Other Logic Requirement
        // NOT|AND -> !(a && b) -> !a || !b
        // NOT|OR -> !(a || b) -> !a && !b
        return thisRequirement == null ? new NOTRequirement(innerRequirement) : thisRequirement;
    }

    @Override
    public int getDepth() {
        if (requirement instanceof ILogicRequirement) {
            return 1 + ((ILogicRequirement) requirement).getDepth();
        }
        return 1;
    }
}
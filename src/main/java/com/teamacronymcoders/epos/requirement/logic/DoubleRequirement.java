package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO: Do we want to add a "simplify" method that returns a list of IRequirement, for use in our combiner
// This would be specifically for our double requirement stuff
public abstract class DoubleRequirement implements IRequirement {

    @Nonnull
    protected final IRequirement leftRequirement;
    @Nonnull
    protected final IRequirement rightRequirement;

    protected DoubleRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        this.leftRequirement = leftRequirement;
        this.rightRequirement = rightRequirement;
    }

    @Nonnull
    public IRequirement getLeftRequirement() {
        return leftRequirement;
    }

    @Nonnull
    public IRequirement getRightRequirement() {
        return rightRequirement;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return true;
    }

    @Nonnull
    @Override
    public RequirementComparison compare(IRequirement other) {
        if (other == this) {
            return RequirementComparison.IDENTICAL;
        }
        RequirementComparison comparison = getSingleComparison(getSingleComparison(leftRequirement, other), getSingleComparison(rightRequirement, other));
        //TODO: FIXME: So that this merges with the other comparison properly
        if (comparison != null) {
            return comparison;
        }
        if (other instanceof DoubleRequirement && isInstance((DoubleRequirement) other)) {
            DoubleRequirement otherRequirement = (DoubleRequirement) other;
            comparison = getComparison(getLeftRequirement().compare(otherRequirement.getLeftRequirement()),
                  getRightRequirement().compare(otherRequirement.getRightRequirement()));
            if (comparison != null) {
                return comparison;
            }
            //Check to see if they were just written in the opposite order
            comparison = getComparison(getLeftRequirement().compare(otherRequirement.getRightRequirement()),
                  getRightRequirement().compare(otherRequirement.getLeftRequirement()));
            return comparison != null ? comparison : RequirementComparison.TYPE_MATCHES;
        }
        return RequirementComparison.INCOMPATIBLE;
    }

    protected abstract boolean isInstance(DoubleRequirement requirement);

    @Nullable
    protected abstract RequirementComparison getComparison(RequirementComparison leftComparison, RequirementComparison rightComparison);

    //TODO: Docs, basically is a method that figures out what the overall comparison is based on the pieces
    //TODO: Add support for checking partial matches, which is why canCompareWith always returns true
    // For example if one requirement in an AND requirement is identical to other, then we can have this be MORE_RESTRICTIVE
    @Nullable
    protected abstract RequirementComparison getSingleComparison(RequirementComparison leftComparison, RequirementComparison rightComparison);

    //TODO: Do we want this to be able to have TYPE_MATCHES?
    @Nonnull
    private RequirementComparison getSingleComparison(IRequirement inner, IRequirement other) {
        if (inner.canCompareWith(other)) {
            RequirementComparison comparison = inner.compare(other);
            if (comparison == RequirementComparison.IDENTICAL || comparison == RequirementComparison.LESS_RESTRICTIVE_THAN ||
                comparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
                return comparison;
            }
        }
        if (other.canCompareWith(inner)) {
            RequirementComparison comparison = inner.compare(other);
            if (comparison == RequirementComparison.IDENTICAL) {
                return comparison;
            } else if (comparison == RequirementComparison.MORE_RESTRICTIVE_THAN) {
                return RequirementComparison.LESS_RESTRICTIVE_THAN;
            } else if (comparison == RequirementComparison.LESS_RESTRICTIVE_THAN) {
                return RequirementComparison.MORE_RESTRICTIVE_THAN;
            }
            return RequirementComparison.INCOMPATIBLE;
        }
        return RequirementComparison.INCOMPATIBLE;
    }
}
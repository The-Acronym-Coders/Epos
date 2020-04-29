package com.teamacronymcoders.epos.requirements.logic;

import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.RequirementComparision;
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
    public RequirementComparision compare(IRequirement other) {
        if (other == this) {
            return RequirementComparision.IDENTICAL;
        }
        RequirementComparision comparision = getSingleComparision(getSingleComparision(leftRequirement, other), getSingleComparision(rightRequirement, other));
        //TODO: FIXME: So that this merges with the other comparision properly
        if (comparision != null) {
            return comparision;
        }
        if (other instanceof DoubleRequirement && isInstance((DoubleRequirement) other)) {
            DoubleRequirement otherRequirement = (DoubleRequirement) other;
            comparision = getComparision(getLeftRequirement().compare(otherRequirement.getLeftRequirement()),
                  getRightRequirement().compare(otherRequirement.getRightRequirement()));
            if (comparision != null) {
                return comparision;
            }
            //Check to see if they were just written in the opposite order
            comparision = getComparision(getLeftRequirement().compare(otherRequirement.getRightRequirement()),
                  getRightRequirement().compare(otherRequirement.getLeftRequirement()));
            return comparision != null ? comparision : RequirementComparision.TYPE_MATCHES;
        }
        return RequirementComparision.INCOMPATIBLE;
    }

    protected abstract boolean isInstance(DoubleRequirement requirement);

    @Nullable
    protected abstract RequirementComparision getComparision(RequirementComparision leftComparision, RequirementComparision rightComparision);

    //TODO: Docs, basically is a method that figures out what the overall comparision is based on the pieces
    //TODO: Add support for checking partial matches, which is why canCompareWith always returns true
    // For example if one requirement in an AND requirement is identical to other, then we can have this be MORE_RESTRICTIVE
    @Nullable
    protected abstract RequirementComparision getSingleComparision(RequirementComparision leftComparision, RequirementComparision rightComparision);

    //TODO: Do we want this to be able to have TYPE_MATCHES?
    @Nonnull
    private RequirementComparision getSingleComparision(IRequirement inner, IRequirement other) {
        if (inner.canCompareWith(other)) {
            RequirementComparision comparision = inner.compare(other);
            if (comparision == RequirementComparision.IDENTICAL || comparision == RequirementComparision.LESS_RESTRICTIVE_THAN ||
                comparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {
                return comparision;
            }
        }
        if (other.canCompareWith(inner)) {
            RequirementComparision comparision = inner.compare(other);
            if (comparision == RequirementComparision.IDENTICAL) {
                return comparision;
            } else if (comparision == RequirementComparision.MORE_RESTRICTIVE_THAN) {
                return RequirementComparision.LESS_RESTRICTIVE_THAN;
            } else if (comparision == RequirementComparision.LESS_RESTRICTIVE_THAN) {
                return RequirementComparision.MORE_RESTRICTIVE_THAN;
            }
            return RequirementComparision.INCOMPATIBLE;
        }
        return RequirementComparision.INCOMPATIBLE;
    }
}
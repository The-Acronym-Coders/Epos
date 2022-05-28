package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//TODO: Do we want to add a "simplify" method that returns a list of IRequirement, for use in our combiner
// This would be specifically for our double requirement stuff
public abstract class DoubleRequirement implements ILogicRequirement {//TODO: Implement equals and hashCode?

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
        } else if (other instanceof DoubleRequirement && isInstance((DoubleRequirement) other)) {
            DoubleRequirement otherRequirement = (DoubleRequirement) other;
            comparison = getComparisonPrechecked(getLeftRequirement().compare(otherRequirement.getLeftRequirement()),
                  getRightRequirement().compare(otherRequirement.getRightRequirement()));
            if (comparison != null) {
                return comparison;
            }
            //Check to see if they were just written in the opposite order
            comparison = getComparisonPrechecked(getLeftRequirement().compare(otherRequirement.getRightRequirement()),
                  getRightRequirement().compare(otherRequirement.getLeftRequirement()));
            return comparison == null ? RequirementComparison.TYPE_MATCHES : comparison;
        }
        return RequirementComparison.INCOMPATIBLE;
    }

    protected abstract boolean isInstance(DoubleRequirement requirement);

    @Nullable
    private RequirementComparison getComparisonPrechecked(RequirementComparison leftComparison, RequirementComparison rightComparison) {
        if (leftComparison == RequirementComparison.IDENTICAL && rightComparison == RequirementComparison.IDENTICAL) {
            //If both requirements are identical then it is identical overall
            return RequirementComparison.IDENTICAL;
        }
        return getComparison(leftComparison, rightComparison);
    }

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
        //TODO: Re-evaluate this
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

    @Nonnull
    @Override
    public IRequirement simplify() {
        IRequirement requirement = simplify(leftRequirement, rightRequirement);
        if (requirement == null) {
            requirement = simplify(rightRequirement, leftRequirement);
            if (requirement == null) {
                return this;
            }
        }
        return requirement;
    }

    @Nullable
    protected abstract IRequirement simplify(@Nonnull IRequirement a, @Nonnull IRequirement b);

    @Nonnull
    protected IRequirement pickSimplest(@Nonnull IRequirement a, @Nonnull IRequirement b) {
        if (a instanceof ILogicRequirement) {
            if (b instanceof ILogicRequirement && ((ILogicRequirement) a).getDepth() <= ((ILogicRequirement) b).getDepth()) {
                return a;
            }
            return b;
        }
        return a;
    }

    @Override
    public int getDepth() {
        int depth = 1;
        if (leftRequirement instanceof ILogicRequirement) {
            depth += ((ILogicRequirement) leftRequirement).getDepth();
        }
        if (rightRequirement instanceof ILogicRequirement) {
            depth += ((ILogicRequirement) rightRequirement).getDepth();
        }
        return depth;
    }
}
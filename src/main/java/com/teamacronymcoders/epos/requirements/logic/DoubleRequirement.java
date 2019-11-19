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

    @Nonnull
    @Override
    public RequirementComparision compare(IRequirement other) {
        if (other == this) {
            return RequirementComparision.IDENTICAL;
        } else if (other instanceof DoubleRequirement && isInstance((DoubleRequirement) other)) {
            DoubleRequirement otherRequirement = (DoubleRequirement) other;
            RequirementComparision comparision = getComparision(getLeftRequirement().compare(otherRequirement.getLeftRequirement()),
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
}
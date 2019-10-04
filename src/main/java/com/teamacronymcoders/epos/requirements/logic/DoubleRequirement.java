package com.teamacronymcoders.epos.requirements.logic;

import com.teamacronymcoders.epos.api.requirements.IRequirement;
import javax.annotation.Nonnull;

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
}
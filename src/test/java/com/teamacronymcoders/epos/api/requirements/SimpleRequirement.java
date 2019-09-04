package com.teamacronymcoders.epos.api.requirements;

import javax.annotation.Nonnull;

/**
 * Simple implementation of IRequirement that can be compared by the name it got passed.
 *
 * Used for testing locks
 */
public class SimpleRequirement implements IRequirement {

    @Nonnull
    private final String name;

    public SimpleRequirement(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof SimpleRequirement && name.equals(((SimpleRequirement) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
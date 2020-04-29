package com.teamacronymcoders.epos.api.locks.keys;

import com.teamacronymcoders.epos.api.locks.IGenericFuzzyLockType;
import javax.annotation.Nonnull;

public final class GenericLockKey implements ILockKey {

    @Nonnull
    private IGenericFuzzyLockType type;

    public GenericLockKey(@Nonnull IGenericFuzzyLockType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof GenericLockKey && type.equals(((GenericLockKey) o).type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
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
        //TODO: Verify this is correct even when used via an enum
        return o == this || o instanceof GenericLockKey && type.equals(((GenericLockKey) o).type);
    }

    @Override
    public int hashCode() {
        //TODO: Verify this is the same even when used via an enum
        return type.hashCode();
    }
}
package com.teamacronymcoders.epos.api.locks.keys;

import javax.annotation.Nonnull;

public class StringLockKey implements ILockKey {

    @Nonnull
    private final String value;

    public StringLockKey(@Nonnull String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof StringLockKey && value.equals(((StringLockKey) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
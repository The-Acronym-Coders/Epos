package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.IGenericFuzzyLockType;
import com.teamacronymcoders.epos.api.locks.key.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DoubleLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(new IGenericFuzzyLockType() {});

    private final double value;

    public DoubleLockKey(double value) {
        this.value = value;
    }

    @Nullable
    public static DoubleLockKey fromObject(@Nonnull Object object) {
        if (object instanceof Double) {
            return new DoubleLockKey((Double) object);
        } else if (object instanceof Integer) {
            return new DoubleLockKey((Integer) object);
        } else if (object instanceof Long) {
            return new DoubleLockKey((Long) object);
        } else if (object instanceof Float) {
            return new DoubleLockKey((Float) object);
        } else if (object instanceof String) {
            try {
                return new DoubleLockKey(Double.parseDouble((String) object));
            } catch (NumberFormatException ignored) {
                //Not a valid double
            }
        }
        return null;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof DoubleLockKey && value >= ((DoubleLockKey) o).value;
    }

    @Nonnull
    @Override
    public ILockKey getNotFuzzy() {
        return NOT_FUZZY;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof DoubleLockKey && value == ((DoubleLockKey) o).value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
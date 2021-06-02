package com.teamacronymcoders.epos.api.locks.key;

import com.teamacronymcoders.epos.api.locks.IGenericFuzzyLockType;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IntegerLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(new IGenericFuzzyLockType() {});

    private final int value;

    public IntegerLockKey(int value) {
        this.value = value;
    }

    @Nullable
    public static IntegerLockKey fromObject(@Nonnull Object object) {
        if (object instanceof Integer) {
            return new IntegerLockKey((Integer) object);
        } else if (object instanceof String) {
            try {
                return new IntegerLockKey(Integer.parseInt((String) object));
            } catch (NumberFormatException ignored) {
                //Not a valid integer
            }
        }
        return null;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof IntegerLockKey && value >= ((IntegerLockKey) o).value;
    }

    @Nonnull
    @Override
    public ILockKey getNotFuzzy() {
        return NOT_FUZZY;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof IntegerLockKey && value == ((IntegerLockKey) o).value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
package com.teamacronymcoders.epos.api.locks.key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StringLockKey implements ILockKey {

    private static final List<StringLockKey> EMPTY = Collections.emptyList();

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

    @Nullable
    public static StringLockKey fromObject(@Nonnull Object object) {
        return object instanceof String ? new StringLockKey((String) object) : null;
    }

    @Nonnull
    public static List<StringLockKey> getKeysFromObject(@Nonnull Object object) {
        if (object instanceof String) {
            String string = (String) object;
            if (!string.contains(",")) {
                return EMPTY;
            }
            List<StringLockKey> keys = new ArrayList<>();
            String[] strings = string.split("\\s*,\\s*");
            for (String s : strings) {
                keys.add(new StringLockKey(s));
            }
            return keys;
        }
        return EMPTY;
    }
}
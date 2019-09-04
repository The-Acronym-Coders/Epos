package com.teamacronymcoders.epos.api.locks.keys;

import javax.annotation.Nonnull;

public interface IFuzzyLockKey extends ILockKey {

    /**
     * Self is the full lock and other is the partial data.
     *
     * @param other The FuzzyLockKey to check.
     *
     * @return True if the two objects count as equals, false otherwise.
     */
    boolean fuzzyEquals(@Nonnull IFuzzyLockKey other);

    /**
     * @return True if the fuzzy data is not used in the current instance of the object, false otherwise.
     */
    boolean isNotFuzzy();

    /**
     * @return A non fuzzy "generic" variant of the key. If there is no generic type return a GenericLockKey instead. Allows for getting partial locks
     */
    @Nonnull
    ILockKey getNotFuzzy();
}
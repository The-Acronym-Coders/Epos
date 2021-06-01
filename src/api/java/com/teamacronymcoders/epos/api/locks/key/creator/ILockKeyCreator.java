package com.teamacronymcoders.epos.api.locks.key.creator;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@FunctionalInterface
public interface ILockKeyCreator<KEY extends ILockKey> {

    /**
     * @return null if it cannot be used to create a type.
     *
     * @implNote IMPORTANT: fail fast where possible, and ensure it is actually the type going to be used
     */
    @Nullable
    KEY createFrom(@Nonnull Object o);
}
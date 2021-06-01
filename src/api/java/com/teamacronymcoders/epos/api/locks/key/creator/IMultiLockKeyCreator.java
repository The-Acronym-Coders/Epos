package com.teamacronymcoders.epos.api.locks.key.creator;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import java.util.Collection;
import javax.annotation.Nonnull;

/**
 * Like {@link ILockKeyCreator} except is used if multiple instances of our key can
 * be created at once from the given object.
 * @param <KEY>
 */
@FunctionalInterface
public interface IMultiLockKeyCreator<KEY extends ILockKey> {

    /**
     * @return empty if it cannot be used to create a type.
     *
     * @implNote IMPORTANT: fail fast where possible, and ensure it is actually the type going to be used
     */
    @Nonnull
    Collection<KEY> createFrom(@Nonnull Object o);
}
package com.teamacronymcoders.epos.api.locks;

import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.api.locks.key.creator.ILockKeyCreator;
import com.teamacronymcoders.epos.api.locks.key.creator.IMultiLockKeyCreator;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;

//TODO: For registerLockType and registerMultiLockType specify to do in common setup and enqueue work
public interface ILockRegistry {//TODO: Re-evaluate javadocs

    /**
     * Unlike the 1.12 system this has it so that each lock key has one registration for each type
     * it can be created from Each one has to be checked when getting by type rather than knowing
     * it "should" be good for that type already
     *
     * @param creator A Lock Key Creator that creates keys of type KEY given an object.
     * @param <KEY>   The type of key the given lock creator is for.
     */
    //TODO: Evaluate if there is any performance impact of checking against all compared to directly getting the subset that works
    void registerLockType(@Nonnull ILockKeyCreator<?> creator);

    /**
     * @param creator A Lock Key Creator that creates multiple keys at once of type KEY given an object.
     * @param <KEY>   The type of key the given lock creator is for.
     */
    <KEY extends ILockKey> void registerMultiLockType(@Nonnull IMultiLockKeyCreator<KEY> creator);

    void addLockByKey(@Nonnull ILockKey key, @Nonnull List<IRequirement> requirements);

    /**
     * Gets the requirements by the the given {@link ILockKey}. If the give key is an {@link IFuzzyLockKey},
     * retrieves all requirements that match; similar to {@link #getFuzzyRequirements(IFuzzyLockKey)}.
     * @param key The {@link ILockKey} to retrieve the requirements of.
     * @return A {@link List} of requirements for the given {@link ILockKey}
     */
    @Nonnull
    List<IRequirement> getRequirementsByKey(@Nonnull ILockKey key);

    /**
     * Gets the requirements by the given {@link IFuzzyLockKey}. This includes the base "non fuzzy"
     * lock key variant and any requirements that are {@link IFuzzyLockKey#fuzzyEquals(IFuzzyLockKey)}
     * to the given key.
     * @param key The {@link IFuzzyLockKey} to retrieve the requirements of.
     * @return A {@link List} of requirements for the given {@link IFuzzyLockKey}
     */
    @Nonnull
    List<IRequirement> getFuzzyRequirements(@Nonnull IFuzzyLockKey key);

    @Nonnull
    List<IRequirement> getLocks(@Nonnull Collection<Object> objects);

    @Nonnull
    List<IRequirement> getLocks(@Nonnull Object object);
}
package com.teamacronymcoders.epos.api.locks;

import com.teamacronymcoders.epos.api.locks.keys.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.api.locks.keys.IParentLockKey;
import com.teamacronymcoders.epos.api.locks.keys.creator.ILockKeyCreator;
import com.teamacronymcoders.epos.api.locks.keys.creator.IMultiLockKeyCreator;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;

//TODO: JavaDoc remaining methods once the RequirementCombiner system is developed,
// as the description for them may change some
public class LockRegistry {

    private static final List<IRequirement> EMPTY_REQUIREMENTS = Collections.emptyList();

    private final List<ILockKeyCreator<? extends ILockKey>> keyCreators = new ArrayList<>();
    private final List<IMultiLockKeyCreator<? extends ILockKey>> multiKeyCreators = new ArrayList<>();
    private final Map<ILockKey, List<IRequirement>> locks = new HashMap<>();
    private final Map<ILockKey, Set<IFuzzyLockKey>> fuzzyLockInfo = new HashMap<>();

    /**
     * Unlike the 1.12 system this has it so that each lock key has one registration for each type
     * it can be created from Each one has to be checked when getting by type rather than knowing
     * it "should" be good for that type already
     *
     * @param creator A Lock Key Creator that creates keys of type KEY given an object.
     * @param <KEY>   The type of key the given lock creator is for.
     */
    //TODO: Evaluate if there is any performance impact of checking against all compared to directly getting the subset that works
    public <KEY extends ILockKey> void registerLockType(@Nonnull ILockKeyCreator<KEY> creator) {
        keyCreators.add(creator);
    }

    /**
     * @param creator A Lock Key Creator that creates multiple keys at once of type KEY given an object.
     * @param <KEY>   The type of key the given lock creator is for.
     */
    public <KEY extends ILockKey> void registerMultiLockType(@Nonnull IMultiLockKeyCreator<KEY> creator) {
        multiKeyCreators.add(creator);
    }

    /**
     * For use in reloading, and unit tests
     */
    public void clearLocks() {
        locks.clear();
        fuzzyLockInfo.clear();
    }

    public void addLockByKey(@Nonnull ILockKey key, @Nonnull List<IRequirement> requirements) {
        //Don't allow having locks on the generic key that is just for when there isn't enough information about a fuzzy lock key
        if (!(key instanceof GenericLockKey) && !requirements.isEmpty()) {
            locks.put(key, requirements);
            if (key instanceof IFuzzyLockKey) {
                IFuzzyLockKey fuzzy = (IFuzzyLockKey) key;
                if (!fuzzy.isNotFuzzy()) {
                    //Store the fuzzy instance in a list for the specific item
                    fuzzyLockInfo.computeIfAbsent(fuzzy.getNotFuzzy(), k -> new HashSet<>()).add(fuzzy);
                }
            }
        }
    }

    /**
     * Gets the requirements by the the given {@link ILockKey}. If the give key is an {@link IFuzzyLockKey},
     * retrieves all requirements that match; similar to {@link #getFuzzyRequirements(IFuzzyLockKey)}.
     * @param key The {@link ILockKey} to retrieve the requirements of.
     * @return A {@link List} of requirements for the given {@link ILockKey}
     * @apiNote This method does not have special handling for {@link IParentLockKey} as parent lock keys
     * should not be directly initialized, and are for internal use via the lock key creators.
     */
    @Nonnull
    public List<IRequirement> getRequirementsByKey(@Nonnull ILockKey key) {
        if (key instanceof IFuzzyLockKey) {
            return getFuzzyRequirements((IFuzzyLockKey) key);
        }
        return locks.getOrDefault(key, EMPTY_REQUIREMENTS);
    }

    /**
     * Gets the requirements by the given {@link IFuzzyLockKey}. This includes the base "non fuzzy"
     * lock key variant and any requirements that are {@link IFuzzyLockKey#fuzzyEquals(IFuzzyLockKey)}
     * to the given key.
     * @param key The {@link IFuzzyLockKey} to retrieve the requirements of.
     * @return A {@link List} of requirements for the given {@link IFuzzyLockKey}
     */
    @Nonnull
    public List<IRequirement> getFuzzyRequirements(@Nonnull IFuzzyLockKey key) {
        List<IRequirement> requirements = new ArrayList<>();
        if (key.isNotFuzzy()) {
            addRequirementsFromLock(requirements, key);
        } else {
            ILockKey baseLock = key.getNotFuzzy();
            //Add the base lock's requirements
            addRequirementsFromLock(requirements, baseLock);
            Set<IFuzzyLockKey> fuzzyLookup = fuzzyLockInfo.get(baseLock);
            if (fuzzyLookup != null) {
                for (IFuzzyLockKey fuzzyLock : fuzzyLookup) {
                    if (key.fuzzyEquals(fuzzyLock)) { //Build up the best match
                        //fuzzy is the given object and has all info and fuzzyLock is the partial information
                        addRequirementsFromLock(requirements, fuzzyLock);
                    }
                }
            }
        }
        return requirements;
    }

    @Nonnull
    public List<IRequirement> getLocks(@Nonnull Collection<Object> objects) {
        //TODO: Helper method for combining the locks of multiple same type objects (Maybe even allow of different type objects)
        // If it doesn't support different type objects should there be some optimization so it doesn't recheck all the types
        List<IRequirement> requirements = new ArrayList<>();
        for (Object object : objects) {
            requirements.addAll(getLocks(object));
        }
        return requirements;
    }

    @Nonnull
    public List<IRequirement> getLocks(@Nonnull Object object) {
        List<IRequirement> requirements = new ArrayList<>();
        for (ILockKeyCreator<? extends ILockKey> keyCreator : keyCreators) {
            ILockKey lockKey = keyCreator.createFrom(object);
            if (lockKey != null) {
                //We didn't fail to create a lock key with our object,
                // so we can add any requirements we find to our list of requirements.
                addRequirementsFromCreatedKey(requirements, lockKey);
            }
        }
        //Handle any multi keys
        for (IMultiLockKeyCreator<? extends ILockKey> keyCreator : multiKeyCreators) {
            Collection<? extends ILockKey> lockKeys = keyCreator.createFrom(object);
            for (ILockKey lockKey : lockKeys) {
                addRequirementsFromCreatedKey(requirements, lockKey);
            }
        }
        return requirements;
    }

    /**
     * Figures out what kind of key the given key is, and retrieves the proper set of requirements
     * for it and adds them to the given requirement list.
     * @param requirements List of requirements to add to
     * @param key The lock key to get the requirements of.
     */
    private void addRequirementsFromCreatedKey(@Nonnull List<IRequirement> requirements, @Nonnull ILockKey key) {
        if (key instanceof IFuzzyLockKey) {
            requirements.addAll(getFuzzyRequirements((IFuzzyLockKey) key));
        } else {
            addRequirementsFromLock(requirements, key);
        }
        if (key instanceof IParentLockKey) {
            //Add all the sub requirements the lock key may have
            // (if there is one that matches the key itself it will have been caught above)
            requirements.addAll(((IParentLockKey) key).getSubRequirements());
        }
    }

    /**
     * Helper method to add a lock's requirements to the requirement list.
     * If there are no requirements for the given key, do nothing.
     * @param requirements List of requirements to add to
     * @param key The lock key to get the requirements of.
     */
    private void addRequirementsFromLock(@Nonnull List<IRequirement> requirements, @Nonnull ILockKey key) {
        List<IRequirement> reqs = locks.get(key);
        if (reqs != null) {
            //If there are locks for the key add them
            requirements.addAll(reqs);
        }
    }
}
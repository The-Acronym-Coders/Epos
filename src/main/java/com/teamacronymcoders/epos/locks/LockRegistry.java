package com.teamacronymcoders.epos.locks;

import com.teamacronymcoders.epos.api.locks.ILockRegistry;
import com.teamacronymcoders.epos.api.locks.key.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.api.locks.key.creator.ILockKeyCreator;
import com.teamacronymcoders.epos.api.locks.key.creator.IMultiLockKeyCreator;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
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
public class LockRegistry implements ILockRegistry {

    public static final LockRegistry INSTANCE = new LockRegistry();

    private static final List<IRequirement> EMPTY_REQUIREMENTS = Collections.emptyList();

    private final List<ILockKeyCreator<? extends ILockKey>> keyCreators = new ArrayList<>();
    private final List<IMultiLockKeyCreator<? extends ILockKey>> multiKeyCreators = new ArrayList<>();
    private final Map<ILockKey, List<IRequirement>> locks = new HashMap<>();
    private final Map<ILockKey, Set<IFuzzyLockKey>> fuzzyLockInfo = new HashMap<>();

    @Override
    public <KEY extends ILockKey> void registerLockType(@Nonnull ILockKeyCreator<KEY> creator) {
        keyCreators.add(creator);
    }

    @Override
    public <KEY extends ILockKey> void registerMultiLockType(@Nonnull IMultiLockKeyCreator<KEY> creator) {
        multiKeyCreators.add(creator);
    }

    /**
     * For use in reloading, and unit tests
     */
    public void clearLocks() {//TODO: Double check but I don't think this needs to be exposed to the ILockRegistry
        locks.clear();
        fuzzyLockInfo.clear();
    }

    @Override
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

    @Nonnull
    @Override
    public List<IRequirement> getRequirementsByKey(@Nonnull ILockKey key) {
        if (key instanceof IFuzzyLockKey) {
            return getFuzzyRequirements((IFuzzyLockKey) key);
        }
        return locks.getOrDefault(key, EMPTY_REQUIREMENTS);
    }

    @Nonnull
    @Override
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
    @Override
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
    @Override
    public List<IRequirement> getLocks(@Nonnull Object object) {
        List<IRequirement> requirements = new ArrayList<>();
        for (ILockKeyCreator<? extends ILockKey> keyCreator : keyCreators) {
            ILockKey lockKey = keyCreator.createFrom(object);
            if (lockKey != null) {
                //We didn't fail to create a lock key with our object,
                // so we can add any requirements we find to our list of requirements.
                requirements.addAll(getRequirementsByKey(lockKey));
            }
        }
        //Handle any multi keys
        for (IMultiLockKeyCreator<? extends ILockKey> keyCreator : multiKeyCreators) {
            Collection<? extends ILockKey> lockKeys = keyCreator.createFrom(object);
            for (ILockKey lockKey : lockKeys) {
                requirements.addAll(getRequirementsByKey(lockKey));
            }
        }
        return requirements;
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
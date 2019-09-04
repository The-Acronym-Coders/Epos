package com.teamacronymcoders.epos.api.locks;

import com.teamacronymcoders.epos.api.locks.keys.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.api.locks.keys.IParentLockKey;
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

public class LockRegistry {

    private static final List<IRequirement> EMPTY_REQUIREMENTS = Collections.emptyList();

    private final List<ILockKeyCreator<? extends ILockKey>> keyCreators = new ArrayList<>();
    private final Map<ILockKey, List<IRequirement>> locks = new HashMap<>();
    private final Map<ILockKey, Set<IFuzzyLockKey>> fuzzyLockInfo = new HashMap<>();

    public <KEY extends ILockKey> void registerLockType(@Nonnull ILockKeyCreator<KEY> creator) {
        keyCreators.add(creator);
        //Unlike the 1.12 system this has it so that each lock key has one registration for each type it can be created from
        //Each one has to be checked when getting by type rather than knowing it "should" be good for that type already
        //Does this have any negative performance impact
    }

    /**
     * For use in reloading, and unit tests
     */
    public void clearLocks() {
        locks.clear();
        fuzzyLockInfo.clear();
    }

    public void addLockByKey(@Nonnull ILockKey key, @Nonnull List<IRequirement> requirements) {
        if (key instanceof GenericLockKey || requirements.isEmpty()) {
            //Don't allow having locks on the generic key that is just for when there isn't enough information about a fuzzy lock key
            return;
        }
        locks.put(key, requirements);

        if (key instanceof IFuzzyLockKey) {
            IFuzzyLockKey fuzzy = (IFuzzyLockKey) key;
            if (!fuzzy.isNotFuzzy()) {
                //Store the fuzzy instance in a list for the specific item
                fuzzyLockInfo.computeIfAbsent(fuzzy.getNotFuzzy(), k -> new HashSet<>()).add(fuzzy);
            }
        }
    }

    @Nonnull
    public List<IRequirement> getRequirementsByKey(@Nonnull ILockKey key) {
        if (key instanceof IFuzzyLockKey) {
            return getFuzzyRequirements((IFuzzyLockKey) key);
        }
        return locks.getOrDefault(key, EMPTY_REQUIREMENTS);
    }

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

    //Helper method to add a lock's requirements to the requirement list if it has them
    private void addRequirementsFromLock(@Nonnull List<IRequirement> requirements, @Nonnull ILockKey key) {
        List<IRequirement> reqs = locks.get(key);
        if (reqs != null) {
            //If there are locks for the key add them
            requirements.addAll(reqs);
        }
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
            if (lockKey == null) {
                //We failed to create a lock key with our object, continue to next type of lock key
                continue;
            }
            if (lockKey instanceof IFuzzyLockKey) {
                requirements.addAll(getFuzzyRequirements((IFuzzyLockKey) lockKey));
            } else {
                addRequirementsFromLock(requirements, lockKey);
            }
            if (lockKey instanceof IParentLockKey) {
                //Add all the sub requirements the lock key may have
                // (if there is one that matches the key itself it will have been caught above)
                requirements.addAll(((IParentLockKey) lockKey).getSubRequirements());
            }
        }
        return requirements;
    }
}
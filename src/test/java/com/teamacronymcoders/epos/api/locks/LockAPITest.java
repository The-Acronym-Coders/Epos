package com.teamacronymcoders.epos.api.locks;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.locks.keys.DoubleLockKey;
import com.teamacronymcoders.epos.api.locks.keys.IntegerLockKey;
import com.teamacronymcoders.epos.api.locks.keys.StringLockKey;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.SimpleRequirement;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Lock Registry.")
class LockAPITest {

    @BeforeAll
    static void registerLockTypes() {
        EposAPI.LOCK_REGISTRY.registerLockType(DoubleLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(IntegerLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(StringLockKey::fromObject);

        EposAPI.LOCK_REGISTRY.registerMultiLockType(StringLockKey::getKeysFromObject);
    }

    /**
     * Ensure that the lock registry has no lock keys in it so that we don't leak keys into the next test.
     */
    @AfterEach
    void resetLocks() {
        EposAPI.LOCK_REGISTRY.clearLocks();
    }

    @Test
    @DisplayName("Test adding, and then retrieving by a simple lock key")
    void simpleAddRetrieve() {
        StringLockKey lockKey = new StringLockKey("Key");
        IRequirement requirement = new SimpleRequirement("Requirement");
        EposAPI.LOCK_REGISTRY.addLockByKey(lockKey, Collections.singletonList(requirement));
        List<IRequirement> fromKey = EposAPI.LOCK_REGISTRY.getRequirementsByKey(lockKey);
        Assertions.assertEquals(fromKey.size(), 1);
        //It is not empty as we know it is the same size as our initial
        Assertions.assertTrue(fromKey.contains(requirement));
    }

    @Test
    @DisplayName("Test retrieval via multi lock creator system")
    void fromParentLock() {
        EposAPI.LOCK_REGISTRY.addLockByKey(new StringLockKey("1"), Collections.singletonList(new SimpleRequirement("1")));
        EposAPI.LOCK_REGISTRY.addLockByKey(new StringLockKey("2"), Collections.singletonList(new SimpleRequirement("2")));
        EposAPI.LOCK_REGISTRY.addLockByKey(new StringLockKey("3"), Collections.singletonList(new SimpleRequirement("3")));
        List<IRequirement> fromKey = EposAPI.LOCK_REGISTRY.getLocks("1,2,3");
        Assertions.assertEquals(fromKey.size(), 3);
    }

    @Test
    @DisplayName("Test retrieval via FuzzyLockKey")
    void fromFuzzyLock() {
        IRequirement bigRequirement = new SimpleRequirement("Requirement Big");
        IRequirement smallRequirement = new SimpleRequirement("Requirement Small");
        EposAPI.LOCK_REGISTRY.addLockByKey(new IntegerLockKey(5), Collections.singletonList(bigRequirement));
        EposAPI.LOCK_REGISTRY.addLockByKey(new IntegerLockKey(3), Collections.singletonList(smallRequirement));
        List<IRequirement> fromKey = EposAPI.LOCK_REGISTRY.getRequirementsByKey(new IntegerLockKey(7));
        Assertions.assertEquals(fromKey.size(), 2);
        //It is not empty as we know it is the same size as our initial
        Assertions.assertTrue(fromKey.contains(bigRequirement) && fromKey.contains(smallRequirement));
    }

    @Test
    @DisplayName("Test retrieval across multiple lock types.")
    void multiRetrieval() {
        EposAPI.LOCK_REGISTRY.addLockByKey(new IntegerLockKey(1), Collections.singletonList(new SimpleRequirement("1")));
        EposAPI.LOCK_REGISTRY.addLockByKey(new DoubleLockKey(3), Collections.singletonList(new SimpleRequirement("2")));
        EposAPI.LOCK_REGISTRY.addLockByKey(new StringLockKey("3"), Collections.singletonList(new SimpleRequirement("3")));
        List<IRequirement> fromKey = EposAPI.LOCK_REGISTRY.getLocks("3");
        Assertions.assertEquals(fromKey.size(), 3);
    }
}
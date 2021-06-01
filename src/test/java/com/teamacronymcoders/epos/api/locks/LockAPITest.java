package com.teamacronymcoders.epos.api.locks;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.locks.key.DoubleLockKey;
import com.teamacronymcoders.epos.api.locks.key.IntegerLockKey;
import com.teamacronymcoders.epos.api.locks.key.StringLockKey;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.SimpleRequirement;
import com.teamacronymcoders.epos.locks.LockRegistry;
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
        ILockRegistry lockRegistry = EposAPI.getLockRegistry();
        lockRegistry.registerLockType(DoubleLockKey::fromObject);
        lockRegistry.registerLockType(IntegerLockKey::fromObject);
        lockRegistry.registerLockType(StringLockKey::fromObject);
        lockRegistry.registerMultiLockType(StringLockKey::getKeysFromObject);
    }

    /**
     * Ensure that the lock registry has no lock keys in it so that we don't leak keys into the next test.
     */
    @AfterEach
    void resetLocks() {
        LockRegistry.INSTANCE.clearLocks();
    }

    @Test
    @DisplayName("Test adding, and then retrieving by a simple lock key")
    void simpleAddRetrieve() {
        StringLockKey lockKey = new StringLockKey("Key");
        IRequirement requirement = new SimpleRequirement("Requirement");
        ILockRegistry lockRegistry = EposAPI.getLockRegistry();
        lockRegistry.addLockByKey(lockKey, Collections.singletonList(requirement));
        List<IRequirement> fromKey = lockRegistry.getRequirementsByKey(lockKey);
        Assertions.assertEquals(fromKey.size(), 1);
        //It is not empty as we know it is the same size as our initial
        Assertions.assertTrue(fromKey.contains(requirement));
    }

    @Test
    @DisplayName("Test retrieval via multi lock creator system")
    void fromParentLock() {
        ILockRegistry lockRegistry = EposAPI.getLockRegistry();
        lockRegistry.addLockByKey(new StringLockKey("1"), Collections.singletonList(new SimpleRequirement("1")));
        lockRegistry.addLockByKey(new StringLockKey("2"), Collections.singletonList(new SimpleRequirement("2")));
        lockRegistry.addLockByKey(new StringLockKey("3"), Collections.singletonList(new SimpleRequirement("3")));
        List<IRequirement> fromKey = lockRegistry.getLocks("1,2,3");
        Assertions.assertEquals(fromKey.size(), 3);
    }

    @Test
    @DisplayName("Test retrieval via FuzzyLockKey")
    void fromFuzzyLock() {
        ILockRegistry lockRegistry = EposAPI.getLockRegistry();
        IRequirement bigRequirement = new SimpleRequirement("Requirement Big");
        IRequirement smallRequirement = new SimpleRequirement("Requirement Small");
        lockRegistry.addLockByKey(new IntegerLockKey(5), Collections.singletonList(bigRequirement));
        lockRegistry.addLockByKey(new IntegerLockKey(3), Collections.singletonList(smallRequirement));
        List<IRequirement> fromKey = lockRegistry.getRequirementsByKey(new IntegerLockKey(7));
        Assertions.assertEquals(fromKey.size(), 2);
        //It is not empty as we know it is the same size as our initial
        Assertions.assertTrue(fromKey.contains(bigRequirement) && fromKey.contains(smallRequirement));
    }

    @Test
    @DisplayName("Test retrieval across multiple lock types.")
    void multiRetrieval() {
        ILockRegistry lockRegistry = EposAPI.getLockRegistry();
        lockRegistry.addLockByKey(new IntegerLockKey(1), Collections.singletonList(new SimpleRequirement("1")));
        lockRegistry.addLockByKey(new DoubleLockKey(3), Collections.singletonList(new SimpleRequirement("2")));
        lockRegistry.addLockByKey(new StringLockKey("3"), Collections.singletonList(new SimpleRequirement("3")));
        List<IRequirement> fromKey = lockRegistry.getLocks("3");
        Assertions.assertEquals(fromKey.size(), 3);
    }
}
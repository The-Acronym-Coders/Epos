package com.teamacronymcoders.epos.api.locks;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.locks.keys.DoubleLockKey;
import com.teamacronymcoders.epos.api.locks.keys.IntegerLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ListLockKey;
import com.teamacronymcoders.epos.api.locks.keys.StringLockKey;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.SimpleRequirement;
import java.util.ArrayList;
import java.util.List;
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
        EposAPI.LOCK_REGISTRY.registerLockType(ListLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(object -> object instanceof String ? new StringLockKey((String) object) : null);
    }

    @Test
    @DisplayName("Test adding, and then retrieving by a simple lock key")
    void simpleAddRetrieve() {
        StringLockKey lockKey = new StringLockKey("Key");
        List<IRequirement> requirements = new ArrayList<>();
        String name = "Requirement";
        requirements.add(new SimpleRequirement(name));
        EposAPI.LOCK_REGISTRY.addLockByKey(lockKey, requirements);
        List<IRequirement> fromKey = EposAPI.LOCK_REGISTRY.getRequirementsByKey(lockKey);
        Assertions.assertEquals(requirements.size(), fromKey.size());
        //It is not empty as we know it is the same size as our initial
        IRequirement requirement = fromKey.get(0);
        Assertions.assertTrue(requirement instanceof SimpleRequirement);
        Assertions.assertEquals(name, ((SimpleRequirement) requirement).getName());
    }
}
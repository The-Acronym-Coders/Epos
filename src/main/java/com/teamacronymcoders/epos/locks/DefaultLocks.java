package com.teamacronymcoders.epos.locks;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.locks.keys.ArmorLockKey;
import com.teamacronymcoders.epos.locks.keys.ArmorToughnessLockKey;
import com.teamacronymcoders.epos.locks.keys.AttackDamageLockKey;
import com.teamacronymcoders.epos.locks.keys.BlockStateLockKey;
import com.teamacronymcoders.epos.locks.keys.DimensionTypeLockKey;
import com.teamacronymcoders.epos.locks.keys.GenericNBTLockKey;
import com.teamacronymcoders.epos.locks.keys.HungerLockKey;
import com.teamacronymcoders.epos.locks.keys.ItemLockKey;
import com.teamacronymcoders.epos.locks.keys.ModLockKey;
import com.teamacronymcoders.epos.locks.keys.SaturationLockKey;
import com.teamacronymcoders.epos.locks.keys.TagLockKey;
import com.teamacronymcoders.epos.locks.keys.entity.EntityDamageKey;
import com.teamacronymcoders.epos.locks.keys.entity.EntityMountKey;
import com.teamacronymcoders.epos.locks.keys.entity.EntityTameKey;
import com.teamacronymcoders.epos.locks.keys.harvest.BlockHarvestLockKey;
import com.teamacronymcoders.epos.locks.keys.harvest.ToolHarvestLockKey;

public class DefaultLocks {

    public static void registerKeyLookups() {
        EposAPI.LOCK_REGISTRY.registerLockType(ItemLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(BlockStateLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(ModLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(GenericNBTLockKey::fromObject);

        //Ones that probably will be in an addon but for now are from CompatSkills for more test cases of seeing how the system works
        EposAPI.LOCK_REGISTRY.registerLockType(DimensionTypeLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(EntityDamageKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(EntityMountKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(EntityTameKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(ArmorLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(ArmorToughnessLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(AttackDamageLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(HungerLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(SaturationLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(BlockHarvestLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerMultiLockType(ToolHarvestLockKey::getKeysFromObject);
        EposAPI.LOCK_REGISTRY.registerMultiLockType(TagLockKey::getKeysFromObject);
    }
}
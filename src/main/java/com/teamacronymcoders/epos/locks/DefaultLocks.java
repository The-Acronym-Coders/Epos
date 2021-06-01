package com.teamacronymcoders.epos.locks;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.locks.ILockRegistry;
import com.teamacronymcoders.epos.locks.key.ArmorLockKey;
import com.teamacronymcoders.epos.locks.key.ArmorToughnessLockKey;
import com.teamacronymcoders.epos.locks.key.AttackDamageLockKey;
import com.teamacronymcoders.epos.locks.key.BlockStateLockKey;
import com.teamacronymcoders.epos.locks.key.DimensionTypeLockKey;
import com.teamacronymcoders.epos.locks.key.GenericNBTLockKey;
import com.teamacronymcoders.epos.locks.key.HungerLockKey;
import com.teamacronymcoders.epos.locks.key.ItemLockKey;
import com.teamacronymcoders.epos.locks.key.ModLockKey;
import com.teamacronymcoders.epos.locks.key.SaturationLockKey;
import com.teamacronymcoders.epos.locks.key.TagLockKey;
import com.teamacronymcoders.epos.locks.key.entity.EntityDamageKey;
import com.teamacronymcoders.epos.locks.key.entity.EntityMountKey;
import com.teamacronymcoders.epos.locks.key.entity.EntityTameKey;
import com.teamacronymcoders.epos.locks.key.harvest.BlockHarvestLockKey;
import com.teamacronymcoders.epos.locks.key.harvest.ToolHarvestLockKey;

public class DefaultLocks {

    public static void registerKeyLookups() {
        ILockRegistry lockRegistry = EposAPI.getLockRegistry();
        lockRegistry.registerLockType(ItemLockKey::fromObject);
        lockRegistry.registerLockType(BlockStateLockKey::fromObject);
        lockRegistry.registerLockType(ModLockKey::fromObject);
        lockRegistry.registerLockType(GenericNBTLockKey::fromObject);

        //Ones that probably will be in an addon but for now are from CompatSkills for more test cases of seeing how the system works
        lockRegistry.registerLockType(DimensionTypeLockKey::fromObject);
        lockRegistry.registerLockType(EntityDamageKey::fromObject);
        lockRegistry.registerLockType(EntityMountKey::fromObject);
        lockRegistry.registerLockType(EntityTameKey::fromObject);
        lockRegistry.registerLockType(ArmorLockKey::fromObject);
        lockRegistry.registerLockType(ArmorToughnessLockKey::fromObject);
        lockRegistry.registerLockType(AttackDamageLockKey::fromObject);
        lockRegistry.registerLockType(HungerLockKey::fromObject);
        lockRegistry.registerLockType(SaturationLockKey::fromObject);
        lockRegistry.registerLockType(BlockHarvestLockKey::fromObject);
        lockRegistry.registerMultiLockType(ToolHarvestLockKey::getKeysFromObject);
        lockRegistry.registerMultiLockType(TagLockKey::getKeysFromObject);
    }
}
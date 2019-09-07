package com.teamacronymcoders.epos.locks;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.locks.keys.ArmorLockKey;
import com.teamacronymcoders.epos.locks.keys.ArmorToughnessLockKey;
import com.teamacronymcoders.epos.locks.keys.AttackDamageLockKey;
import com.teamacronymcoders.epos.locks.keys.DimensionTypeLockKey;
import com.teamacronymcoders.epos.locks.keys.GenericNBTLockKey;
import com.teamacronymcoders.epos.locks.keys.HungerLockKey;
import com.teamacronymcoders.epos.locks.keys.ItemLockKey;
import com.teamacronymcoders.epos.locks.keys.ModLockKey;
import com.teamacronymcoders.epos.locks.keys.SaturationLockKey;
import com.teamacronymcoders.epos.locks.keys.entity.EntityDamageKey;
import com.teamacronymcoders.epos.locks.keys.entity.EntityMountKey;
import com.teamacronymcoders.epos.locks.keys.entity.EntityTameKey;
import com.teamacronymcoders.epos.locks.keys.harvest.BlockHarvestLockKey;
import com.teamacronymcoders.epos.locks.keys.harvest.ToolHarvestLockKey;
import com.teamacronymcoders.epos.locks.keys.tag.ParentTagLockKey;
import net.minecraft.item.ItemStack;

public class DefaultLocks {

    public static void registerKeyLookups() {
        EposAPI.LOCK_REGISTRY.registerLockType(ItemLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(ModLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(GenericNBTLockKey::fromObject);

        //Ones that probably will be in an addon but for now are from CompatSkills for more test cases of seeing how the system works
        EposAPI.LOCK_REGISTRY.registerLockType(DimensionTypeLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(EntityDamageKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(EntityMountKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(EntityTameKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(object -> object instanceof ItemStack ? ArmorLockKey.fromItemStack((ItemStack) object) : null);
        EposAPI.LOCK_REGISTRY.registerLockType(object -> object instanceof ItemStack ? ArmorToughnessLockKey.fromItemStack((ItemStack) object) : null);
        EposAPI.LOCK_REGISTRY.registerLockType(object -> object instanceof ItemStack ? AttackDamageLockKey.fromItemStack((ItemStack) object) : null);
        EposAPI.LOCK_REGISTRY.registerLockType(HungerLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(SaturationLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(BlockHarvestLockKey::fromObject);
        EposAPI.LOCK_REGISTRY.registerLockType(object -> object instanceof ItemStack ? ToolHarvestLockKey.fromItemStack((ItemStack) object) : null);
        EposAPI.LOCK_REGISTRY.registerLockType(ParentTagLockKey::fromObject);
    }
}
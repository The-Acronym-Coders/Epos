package com.teamacronymcoders.epos.impl.feat.ranger;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.living.LivingEvent;

// TODO: Rework to not read and write to NBT every Tick
public class MansBestFriend {
    private static final String REMAINING = "mbf_remaining";

    public static final EventManager.ISubscribe featManager = EventManager.create(LivingEvent.LivingUpdateEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
               if (event.getEntityLiving() != null && event.getEntityLiving() instanceof WolfEntity) {
                   WolfEntity wolf = (WolfEntity) event.getEntityLiving();
                   if (wolf.isTame()) {
                       LivingEntity owner = wolf.getOwner();
                       return owner != null && EposCharacterUtil.hasFeat(owner, EposFeatIds.MANS_BEST_FRIEND);
                   }
               }
               return false;
            })
            .process(event -> {
                WolfEntity wolf = (WolfEntity) event.getEntityLiving();
                LivingEntity owner = wolf.getOwner();
                CompoundNBT persistent = wolf.getPersistentData();
                boolean isCloseToOwner = wolf.blockPosition().closerThan(owner.blockPosition(), 10d);
                boolean isMissingHealth = wolf.getHealth() < wolf.getMaxHealth();

                if (persistent.contains(REMAINING)) {
                    int remainingTime = persistent.getInt(REMAINING);
                    if (remainingTime <= 0 && isMissingHealth) {
                        if (isCloseToOwner) {
                            wolf.heal(0.5f);
                        }
                    } else {
                        if (isMissingHealth) {
                            remainingTime--;
                            persistent.putInt(REMAINING, remainingTime);
                        }
                    }

                } else {
                    if (isCloseToOwner && isMissingHealth) {
                        wolf.heal(0.5f);
                    }
                    persistent.putInt(REMAINING, 10);
                }
            });
}

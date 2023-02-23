package com.teamacronymcoders.epos.impl.feat.ranger;

import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.impl.featinfo.MBFFeatInfo;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

// TODO: Rework to not read and write to NBT every Tick
public class MansBestFriend {

    public static void registerFeatManagers() {
        featManager.subscribe();
        featInfoManager.subscribe();
    }

    private static final EventManager.ISubscribe featManager = EventManager.create(LivingEvent.LivingUpdateEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
               if (event.getEntityLiving() != null && event.getEntityLiving() instanceof Wolf) {
                   Wolf wolf = (Wolf) event.getEntityLiving();
                   if (wolf.isTame()) {
                       LivingEntity owner = wolf.getOwner();
                       return owner != null && EposCharacterUtil.hasFeat(owner, EposFeatIds.MANS_BEST_FRIEND);
                   }
               }
               return false;
            })
            .process(event -> {
                Wolf wolf = (Wolf) event.getEntityLiving();
                LivingEntity owner = wolf.getOwner();
                FeatInfo info = EposCharacterUtil.getFeatInfo(owner, EposFeatIds.MANS_BEST_FRIEND);
                if (info instanceof MBFFeatInfo) {
                    MBFFeatInfo mbfInfo = (MBFFeatInfo) info;
                    boolean isCloseToOwner = wolf.blockPosition().closerThan(owner.blockPosition(), 10d);
                    boolean isMissingHealth = wolf.getHealth() < wolf.getMaxHealth();
                    int remainingTime = mbfInfo.getRemainingTime();
                    if (remainingTime <= 0 && isMissingHealth && isCloseToOwner) {
                        wolf.heal(0.5f);
                        mbfInfo.setRemainingTime(10);
                    } else {
                        if (isMissingHealth) {
                            mbfInfo.decrementRemainingTime();
                        }
                    }
                }
            });

    private static final EventManager.ISubscribe featInfoManager = EventManager.modGeneric(RegistryEvent.Register.class, FeatInfo.class)
            .process(event -> {
                ((RegistryEvent.Register) event).getRegistry().register(new MBFFeatInfo().setRegistryName(EposFeatIds.MANS_BEST_FRIEND));
            });
}

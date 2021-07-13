package com.teamacronymcoders.epos.impl.feat.ranger;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PackMentality {

    public static final EventManager.ISubscribe featManager = EventManager.create(LivingHurtEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                DamageSource source = event.getSource();
                Entity directSource = source.getDirectEntity();
                if (directSource instanceof WolfEntity) {
                    WolfEntity wolf = (WolfEntity) directSource;
                    if (wolf.isTame()) {
                        LivingEntity owner = wolf.getOwner();
                        return owner != null && EposCharacterUtil.hasFeat(owner, EposFeatIds.PACK_MENTALITY);
                    }
                }
                return false;
            })
            .process(event -> {
                WolfEntity wolf = (WolfEntity) event.getSource().getDirectEntity();
                LivingEntity owner = wolf.getOwner();
                boolean isCloseToOwner = wolf.blockPosition().closerThan(owner.blockPosition(), 10d);
                if (isCloseToOwner) {
                    int nearbyAlliedWolfs = wolf.getCommandSenderWorld().getEntitiesOfClass(
                            WolfEntity.class,
                            AxisAlignedBB.ofSize(wolf.getX(10), wolf.getY(), wolf.getZ(10)),
                            entity ->
                                    entity != null && entity != wolf && entity.isTame()  // Wolf Checks
                                    && entity.getOwnerUUID() != null && entity.getOwnerUUID().equals(owner.getUUID())).size(); // Owner Checks
                    if (nearbyAlliedWolfs > 0) {
                        event.setAmount((event.getAmount() * 1.5f) + Math.min(2, nearbyAlliedWolfs * 0.5f));
                    }
                }
            });
}

package com.teamacronymcoders.epos.impl.feat.ranger;

import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PackMentality {

    public static final EventManager.ISubscribe featManager = EventManager.create(LivingHurtEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                DamageSource source = event.getSource();
                Entity directSource = source.getDirectEntity();
                if (directSource instanceof Wolf) {
                    Wolf wolf = (Wolf) directSource;
                    if (wolf.isTame()) {
                        LivingEntity owner = wolf.getOwner();
                        return owner != null && EposCharacterUtil.hasFeat(owner, EposFeatIds.PACK_MENTALITY);
                    }
                }
                return false;
            })
            .process(event -> {
                Wolf wolf = (Wolf) event.getSource().getDirectEntity();
                LivingEntity owner = wolf.getOwner();
                boolean isCloseToOwner = wolf.blockPosition().closerThan(owner.blockPosition(), 10d);
                if (isCloseToOwner) {
                    int nearbyAlliedWolfs = wolf.getCommandSenderWorld().getEntitiesOfClass(
                            Wolf.class,
                            AABB.ofSize(new Vec3(wolf.blockPosition().getX(), wolf.blockPosition().getY(), wolf.blockPosition().getZ()), wolf.getX(10), wolf.getY(), wolf.getZ(10)),
                            entity ->
                                    entity != null && entity != wolf && entity.isTame()  // Wolf Checks
                                    && entity.getOwnerUUID() != null && entity.getOwnerUUID().equals(owner.getUUID())).size(); // Owner Checks
                    if (nearbyAlliedWolfs > 0) {
                        event.setAmount((event.getAmount() * 1.5f) + Math.min(2, nearbyAlliedWolfs * 0.5f));
                    }
                }
            });
}

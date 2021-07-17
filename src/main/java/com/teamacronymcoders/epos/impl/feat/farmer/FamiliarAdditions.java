package com.teamacronymcoders.epos.impl.feat.farmer;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.impl.featinfo.EOTLFeatInfo;
import com.teamacronymcoders.epos.impl.featinfo.FAFeatInfo;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposUtil;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Chance to make animals breed when you're around(?) + Chance to spawn an extra baby when breeding.
public class FamiliarAdditions {

    public static void registerFeatManagers() {
        breedingManager.subscribe();
        babyManager.subscribe();
        featInfoManager.subscribe();
    }

    private static final EventManager.ISubscribe breedingManager = EventManager.create(TickEvent.PlayerTickEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                if (event.player != null) {
                    PlayerEntity player = event.player;
                    FAFeatInfo info = (FAFeatInfo) EposCharacterUtil.getFeatInfo(player, EposFeatIds.FAMILIAR_ADDITIONS);
                    if (info != null && info.getRemainingTime() <= 0) {
                        World world = player.level;
                        if (world != null) {
                            BlockPos negativePos = player.blockPosition().offset(-2, -1, -2);
                            BlockPos positivePos = player.blockPosition().offset(2, 1, 2);
                            List<AgeableEntity> nearbyEntities = world.getEntitiesOfClass(AgeableEntity.class, new AxisAlignedBB(negativePos, positivePos));
                            info.setRemainingTime(60);
                            return nearbyEntities.size() >= 2 && EposCharacterUtil.hasFeat(player, EposFeatIds.FAMILIAR_ADDITIONS);
                        }
                    } else if (info != null) {
                        info.decrementRemainingTime();
                    }
                }
               return false;
            })
            .process(event -> {
                PlayerEntity player = event.player;
                World world = player.level;
                BlockPos negativePos = player.blockPosition().offset(-2, -1, -2);
                BlockPos positivePos = player.blockPosition().offset(2, 1, 2);
                Map<EntityType<?>, List<AnimalEntity>> sortedEntityMap = new HashMap<>();
                List<AnimalEntity> nearbyEntities = world.getEntitiesOfClass(AnimalEntity.class, new AxisAlignedBB(negativePos, positivePos));
                for (AnimalEntity entity : nearbyEntities) {
                    sortedEntityMap.computeIfAbsent(entity.getType(), type -> new ArrayList<>()).add(entity);
                }
                for (Map.Entry<EntityType<?>, List<AnimalEntity>> entry : sortedEntityMap.entrySet()) {
                    int loveInducedEntities = 0;
                    List<AnimalEntity> entities = entry.getValue();
                    if (entities.size() > 2) {
                        for (AnimalEntity entity : entities) {
                            if (loveInducedEntities < 2) {
                                int age = entity.getAge();
                                if (!world.isClientSide && age == 0 && entity.canFallInLove()) {
                                    entity.setInLove(player);
                                    loveInducedEntities++;
                                }
                            }
                        }
                    }
                }
            });

    private static final EventManager.ISubscribe babyManager = EventManager.create(BabyEntitySpawnEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
               if (event.getCausedByPlayer() != null && event.getChild() != null) {
                   return !event.getCausedByPlayer().level.isClientSide && EposCharacterUtil.hasFeat(event.getCausedByPlayer(), EposFeatIds.FAMILIAR_ADDITIONS) && EposUtil.getRandomizedChance(0.25F);
               }
               return false;
            })
            .process(event -> {
                AgeableEntity child = event.getChild();
                World world = child.level;
                BlockPos pos = child.blockPosition();
                EntityType<?> childType = child.getType();
                AgeableEntity twin = (AgeableEntity) childType.create(world);
                if (twin != null) {
                    twin.setPos(pos.getX(), pos.getY(), pos.getZ());
                    world.addFreshEntity(twin);
                }
            });

    private static final EventManager.ISubscribe featInfoManager = EventManager.modGeneric(RegistryEvent.Register.class, FeatInfo.class)
            .process(event -> {
                ((RegistryEvent.Register) event).getRegistry().register(new FAFeatInfo().setRegistryName(EposFeatIds.FAMILIAR_ADDITIONS));
            });
}

package com.teamacronymcoders.epos.impl.feat.monk;

import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class FistOfCrumblingEarth {
    public static void registerFeatManagers() {
        HARVEST_CHECK_MANAGER.subscribe();
        HARVEST_SPEED_MANAGER.subscribe();
    }

    private static final EventManager.ISubscribe HARVEST_CHECK_MANAGER = EventManager.create(PlayerEvent.HarvestCheck.class, EventManager.Bus.FORGE)
            .filter(event -> EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_CRUMBLING_EARTH))
            .process(event -> {
                Tag<Block> harvestTag = EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_DENSE_EARTH) ? Tiers.NETHERITE.getTag() : Tiers.IRON.getTag();
                if (harvestTag != null && event.getTargetBlock().is(harvestTag) && event.getPlayer().getMainHandItem().isEmpty()) {
                    event.setCanHarvest(true);
                }
            });

    private static final EventManager.ISubscribe HARVEST_SPEED_MANAGER = EventManager.create(PlayerEvent.BreakSpeed.class, EventManager.Bus.FORGE)
            .filter(event -> event.getPlayer() != null && EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_CRUMBLING_EARTH))
            .process(event -> {
                Tag<Block> harvestTag = EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_DENSE_EARTH) ? Tiers.NETHERITE.getTag() : Tiers.IRON.getTag();
                float harvestSpeed = EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_DENSE_EARTH) ? Tiers.NETHERITE.getSpeed() : Tiers.IRON.getSpeed();
                if (harvestTag != null && event.getState().is(harvestTag) && event.getPlayer().getMainHandItem().isEmpty()) {
                    event.setNewSpeed(harvestSpeed);
                }
            });
}

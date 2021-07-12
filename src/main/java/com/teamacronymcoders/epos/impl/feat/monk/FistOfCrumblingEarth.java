package com.teamacronymcoders.epos.impl.feat.monk;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.item.ItemTier;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class FistOfCrumblingEarth {
    public static void registerFeatManagers() {
        HARVEST_CHECK_MANAGER.subscribe();
        HARVEST_SPEED_MANAGER.subscribe();
    }

    private static final EventManager.ISubscribe HARVEST_CHECK_MANAGER = EventManager.create(PlayerEvent.HarvestCheck.class, EventManager.Bus.FORGE)
            .filter(event -> EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_CRUMBLING_EARTH))
            .process(event -> {
                int harvestLevel = EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_DENSE_EARTH) ? ItemTier.NETHERITE.getLevel() : ItemTier.IRON.getLevel();
                if (event.getTargetBlock().getHarvestLevel() <= harvestLevel && event.getPlayer().getMainHandItem().isEmpty()) {
                    event.setCanHarvest(true);
                }
            });

    private static final EventManager.ISubscribe HARVEST_SPEED_MANAGER = EventManager.create(PlayerEvent.BreakSpeed.class, EventManager.Bus.FORGE)
            .filter(event -> event.getPlayer() != null && EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_CRUMBLING_EARTH))
            .process(event -> {
                int harvestLevel = EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_DENSE_EARTH) ? ItemTier.NETHERITE.getLevel() : ItemTier.IRON.getLevel();
                float harvestSpeed = EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.FIST_OF_DENSE_EARTH) ? ItemTier.NETHERITE.getSpeed() : ItemTier.IRON.getSpeed();
                if (event.getState().getHarvestLevel() <= harvestLevel && event.getPlayer().getMainHandItem().isEmpty()) {
                    event.setNewSpeed(harvestSpeed);
                }
            });
}

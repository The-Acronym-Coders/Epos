package com.teamacronymcoders.epos.impl.feat.miner;

import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposBlockUtil;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BlockEvent;

public class CascadingExcavations {
    public static final EventManager.ISubscribe featManager = EventManager.create(BlockEvent.BreakEvent.class, EventManager.Bus.FORGE)
            .filter(event -> EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.CASCADING_EXCAVATIONS))
            .process(event -> {
                ItemStack stack = event.getPlayer().getMainHandItem();
                if (event.getWorld() instanceof Level) {
                    EposBlockUtil.handleCascadingBlockDestruction(stack, (Level) event.getWorld(), event.getState(), event.getPos(), event.getPlayer(), Tags.Blocks.ORES, 125, 25);
                }
            });
}

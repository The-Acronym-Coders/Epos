package com.teamacronymcoders.epos.impl.feat.lumberjack;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.util.EposBlockUtil;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BlockEvent;

public class Timber {
    public static final EventManager.ISubscribe featManager = EventManager.create(BlockEvent.BreakEvent.class, EventManager.Bus.FORGE)
            .filter(event -> EposCharacterUtil.hasFeat(event.getPlayer(), new ResourceLocation(Epos.ID, "timber")))
            .process(event -> {
                ItemStack stack = event.getPlayer().getMainHandItem();
                if (event.getWorld() instanceof World) {
                    EposBlockUtil.handleCascadingBlockDestruction(stack, (World) event.getWorld(), event.getState(), event.getPos(), event.getPlayer(), Tags.Blocks.ORES, 125, 75);
                }
            });
}

package com.teamacronymcoders.eposmajorum.content.lumberjack;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import com.teamacronymcoders.eposmajorum.utils.UtilMethods;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;

public class TimberFeat {
    private static final ResourceLocation RL = new ResourceLocation(EposAPI.ID, "timber");
    private static final Feat FEAT = FeatBuilder.start(RL)
            .withEventHandler(BlockEvent.BreakEvent.class,
                    (breakEvent, entity, iCharacterStats) -> {
                        if (entity.getActiveItemStack().getToolTypes().contains(ToolType.AXE)) {
                            ResourceLocation tag = new ResourceLocation("minecraft", "logs");
                            if (breakEvent.getState().getBlock().getTags().contains(tag)) {
                                BlockPos pos = breakEvent.getPos();
                                World world = breakEvent.getWorld().getWorld();
                                PlayerEntity player = breakEvent.getPlayer();

                                // Runs through blocks, adding valid blocks to the schedueled list to check, and checked blocks to checked.
                                UtilMethods.handleHarvest(pos, world, player);
                            }
                        }
                    })
            .finish();
}
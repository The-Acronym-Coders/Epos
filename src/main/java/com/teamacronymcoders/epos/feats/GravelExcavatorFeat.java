package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import com.teamacronymcoders.epos.utils.helpers.BlockBreakHelper;
import net.minecraft.block.GravelBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayDeque;
import java.util.Deque;

public class GravelExcavatorFeat {
    private static final ResourceLocation RL = new ResourceLocation(EposAPI.ID, "gravel_excavator");
    public static final Feat FEAT =
            FeatBuilder.start(RL)
                .withEventHandler(BlockEvent.BreakEvent.class,
                        (breakEvent, entity, iCharacterStats) -> {
                            if (entity.getActiveItemStack().getToolTypes().contains(ToolType.SHOVEL) && breakEvent.getState().getBlock() instanceof GravelBlock) {
                                World world = breakEvent.getWorld().getWorld();
                                BlockPos pos = breakEvent.getPos();
                                BlockPos holder = pos.up();
                                PlayerEntity player = breakEvent.getPlayer();
                                Deque<BlockPos> posDeque = new ArrayDeque<>();
                                posDeque.add(pos);

                                while (world.getBlockState(holder).getBlock() instanceof GravelBlock) {
                                    posDeque.add(holder);
                                    holder = holder.up();
                                }

                                for (BlockPos pos1 : posDeque) {
                                    BlockBreakHelper.handleBreakBlock(world, holder, world.getBlockState(pos1), player);
                                }
                            }
                        })
                .finish();
}
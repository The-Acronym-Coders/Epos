package com.teamacronymcoders.epos.utils.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import org.antlr.v4.runtime.misc.Array2DHashSet;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockBreakHelper {
    public static void handleBreakBlock(Iterable<BlockPos> iterable, World world, PlayerEntity player) {
        iterable.forEach(blockPos -> handleBreakBlock(world, blockPos, world.getBlockState(blockPos), player));
    }

    private static void handleBreakBlock(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!MinecraftForge.EVENT_BUS.post(new BlockEvent.BreakEvent(world, pos, state, player))) {
            state.removedByPlayer(world, pos, player, true, null);
            state.getBlock().harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getActiveItemStack());
        }
    }

    public static void handleHarvest(BlockPos initialPos, World world, PlayerEntity player) {
        Block block = world.getBlockState(initialPos).getBlock();
        Set<Long> checked = new Array2DHashSet<>();
        List<BlockPos> scheduled = new ArrayList<>();
        scheduled.add(initialPos);
        for (BlockPos pos : scheduled) {
            for (BlockPos mutable : BlockPos.getAllInBoxMutable(
                    pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                    pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1)) {
                long mutablePos = mutable.toLong();
                if (!checked.contains(mutablePos) && world.isAreaLoaded(mutable, 1)) {
                    checked.add(mutablePos);
                    BlockState shiftedState = world.getBlockState(mutable);
                    Block shiftedBlock = shiftedState.getBlock();
                    if (shiftedBlock.equals(block)) {
                        handleBreakBlock(world, mutable, shiftedState, player);
                        scheduled.add(mutable.toImmutable());
                    }
                }
            }
        }
    }
}

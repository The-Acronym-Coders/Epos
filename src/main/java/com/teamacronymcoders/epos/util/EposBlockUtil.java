package com.teamacronymcoders.epos.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;

import java.util.List;

public class EposBlockUtil {

    public static boolean handleCascadingBlockDestruction(ItemStack stack, World world, BlockState state, BlockPos pos, PlayerEntity miner, Tags.IOptionalNamedTag<Block> targetTag, int maxBlocks, int maxRange) {
        if (state.getBlock().is(targetTag)) {
            if (miner instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) miner;
                List<BlockPos> found = EposBlockPosUtil.findPositions(state, pos, world, maxBlocks, maxRange);
                for (BlockPos foundPos : found) {
                    if (pos.equals(foundPos)) {
                        continue;
                    }
                    BlockState foundState = world.getBlockState(foundPos);
                    // serverPlayer.gameMode = serverPlayer.interactionManager
                    int exp = ForgeHooks.onBlockBreakEvent(world, serverPlayer.gameMode.getGameModeForPlayer(), serverPlayer, foundPos);
                    if (exp == -1) {
                        continue;
                    }
                    Block block = foundState.getBlock();
                    TileEntity blockEntity = EposWorldUtil.getTileEntity(world, foundPos);
                    boolean removed = foundState.removedByPlayer(world, foundPos, miner, true, world.getFluidState(foundPos));
                    if (removed) {
                        block.destroy(world, foundPos, foundState);
                        block.playerDestroy(world, miner, foundPos, foundState, blockEntity, stack);
                        miner.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        if (exp > 0 && !world.isClientSide) {
                            block.popExperience((ServerWorld) world, foundPos, exp);
                        }
                    }
                }
            }
        }
        return false;
    }
}

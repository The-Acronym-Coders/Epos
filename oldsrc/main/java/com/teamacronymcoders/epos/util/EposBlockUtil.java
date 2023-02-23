package com.teamacronymcoders.epos.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;

import java.util.List;

public class EposBlockUtil {

    public static boolean handleCascadingBlockDestruction(ItemStack stack, Level level, BlockState state, BlockPos pos, Player miner, Tags.IOptionalNamedTag<Block> targetTag, int maxBlocks, int maxRange) {
        if (state.is(targetTag)) {
            if (miner instanceof ServerPlayer serverPlayer) {
                List<BlockPos> found = EposBlockPosUtil.findPositions(state, pos, level, maxBlocks, maxRange);
                for (BlockPos foundPos : found) {
                    if (pos.equals(foundPos)) {
                        continue;
                    }
                    BlockState foundState = level.getBlockState(foundPos);
                    // serverPlayer.gameMode = serverPlayer.interactionManager
                    int exp = ForgeHooks.onBlockBreakEvent(level, serverPlayer.gameMode.getGameModeForPlayer(), serverPlayer, foundPos);
                    if (exp == -1) {
                        continue;
                    }
                    Block block = foundState.getBlock();
                    BlockEntity blockEntity = EposWorldUtil.getBlockEntity(level, foundPos);
                    boolean removed = foundState.removedByPlayer(level, foundPos, miner, true, level.getFluidState(foundPos));
                    if (removed) {
                        block.destroy(level, foundPos, foundState);
                        block.playerDestroy(level, miner, foundPos, foundState, blockEntity, stack);
                        miner.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        if (exp > 0 && !level.isClientSide) {
                            block.popExperience((ServerLevel) level, foundPos, exp);
                        }
                    }
                }
            }
        }
        return false;
    }
}

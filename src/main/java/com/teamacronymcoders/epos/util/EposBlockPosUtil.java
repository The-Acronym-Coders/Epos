package com.teamacronymcoders.epos.util;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EposBlockPosUtil {

    public static List<BlockPos> findPositions(BlockState state, BlockPos origin, World world, int maxBlocks, int maxRange) {
        List<BlockPos> found = new ArrayList<>();
        Set<BlockPos> checked = new ObjectOpenHashSet<>();
        found.add(origin);
        Block startBlock = state.getBlock();
        int maxCount = maxBlocks - 1;
        for (int i = 0; i < found.size(); i++) {
            BlockPos blockPos = found.get(i);
            checked.add(blockPos);
            for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))) {
                if (!checked.contains(pos)) {
                    if (maxRange == -1 || Math.sqrt(origin.distSqr(pos)) <= maxRange) {
                        if (!world.isEmptyBlock(pos) && startBlock == world.getBlockState(pos).getBlock()) {
                            found.add(pos.immutable());
                            if (found.size() > maxCount) {
                                return found;
                            }
                        }
                    }
                }
            }
        }
        return found;
    }
}

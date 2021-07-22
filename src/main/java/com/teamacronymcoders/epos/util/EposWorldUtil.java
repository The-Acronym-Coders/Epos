package com.teamacronymcoders.epos.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EposWorldUtil {

    public static boolean breakBlock(Level level, BlockPos pos, boolean hasTileEntity, @Nullable Entity entity, ItemStack stack) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.isAir()) {
            return false;
        } else {
            FluidState fluidState = level.getFluidState(pos);
            level.globalLevelEvent(2001, pos, Block.getId(blockstate));
            if (hasTileEntity) {
                BlockEntity blockEntity = blockstate.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                Block.dropResources(blockstate, level, pos, blockEntity, entity, stack);
            }

            return level.setBlock(pos, fluidState.createLegacyBlock(), 3);
        }
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param level - world
     * @param pos   - position
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    public static BlockEntity getBlockEntity(@Nullable Level level, @Nonnull BlockPos pos) {
        if (level == null || level.isEmptyBlock(pos)) {
            return null;
        }
        return level.getBlockEntity(pos);
    }



}

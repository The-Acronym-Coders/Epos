package com.teamacronymcoders.epos.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EposWorldUtil {

    public static boolean breakBlock(World world, BlockPos pos, boolean hasTileEntity, @Nullable Entity entity, ItemStack stack) {
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.getBlock().isAir(blockstate, world, pos)) {
            return false;
        } else {
            FluidState fluidState = world.getFluidState(pos);
            world.globalLevelEvent(2001, pos, Block.getId(blockstate));
            if (hasTileEntity) {
                TileEntity tileentity = blockstate.hasTileEntity() ? world.getBlockEntity(pos) : null;
                Block.dropResources(blockstate, world, pos, tileentity, entity, stack);
            }

            return world.setBlock(pos, fluidState.createLegacyBlock(), 3);
        }
    }

    /**
     * Gets a tile entity if the location is loaded
     *
     * @param world - world
     * @param pos   - position
     * @return tile entity if found, null if either not found or not loaded
     */
    @Nullable
    public static TileEntity getTileEntity(@Nullable World world, @Nonnull BlockPos pos) {
        if (world == null || world.isEmptyBlock(pos)) {
            return null;
        }
        return world.getBlockEntity(pos);
    }



}

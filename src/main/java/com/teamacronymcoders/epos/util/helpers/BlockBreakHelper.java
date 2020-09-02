package com.teamacronymcoders.epos.util.helpers;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import org.antlr.v4.runtime.misc.Array2DHashSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockBreakHelper {
    public static void handleBreakBlock(Iterable<BlockPos> iterable, World world, PlayerEntity player) {
        iterable.forEach(blockPos -> handleBreakBlock(world, blockPos, world.getBlockState(blockPos), player));
    }

    public static void handleBreakBlock(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!MinecraftForge.EVENT_BUS.post(new BlockEvent.BreakEvent(world, pos, state, player))) {
            state.removedByPlayer(world, pos, player, true, null);
            state.getBlock().harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getActiveItemStack());
        }
    }


    /**
     * @param world
     * @param pos
     * @param hasTileEntity
     * @param entity
     * @param stack
     * @return
     */
    public static boolean breakBlock(World world, BlockPos pos, boolean hasTileEntity, @Nullable Entity entity, ItemStack stack) {
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.isAir(world, pos)) {
            return false;
        } else {
            FluidState fluidState = world.getFluidState(pos);
            world.playEvent(2001, pos, Block.getStateId(blockstate));
            if (hasTileEntity) {
                TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
                Block.spawnDrops(blockstate, world, pos, tileentity, entity, stack);
            }

            return world.setBlockState(pos, fluidState.getBlockState(), 3);
        }
    }

    public static void expandedBreakBlocks(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, int scaler) {
        Direction dir = world.rayTraceBlocks(new RayTraceContext(miner.getPositionVec(), new Vector3d(pos.getX(), pos.getY(), pos.getZ()), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, miner)).getFace();
        BlockPos offset = new BlockPos(Vector3d.copy(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, dir.getAxis()).getDirectionVec()).add(1.0, 1.0, 1.0).scale(scaler));
        BlockPos start = pos.add(offset);
        BlockPos end = pos.subtract(offset);
        BlockPos.getAllInBox(start, end)
            .filter(position -> !position.equals(pos) && stack.canHarvestBlock(state))
            .forEach(position -> {
                if (miner instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) miner;
                    if (player instanceof ServerPlayerEntity) {
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                        BlockState foundState = world.getBlockState(position);
                        int exp = ForgeHooks.onBlockBreakEvent(world, serverPlayer.interactionManager.getGameType(), serverPlayer, position);
                        if (exp != -1) {
                            Block block = foundState.getBlock();
                            TileEntity tile = getTileEntity(world, pos);
                            boolean removed = foundState.removedByPlayer(world, position, player, true, world.getFluidState(position));
                            if (removed) {
                                block.onPlayerDestroy(world, position, foundState);
                                block.harvestBlock(world, player, position, foundState, tile, stack);
                                player.addStat(Stats.ITEM_USED.get(stack.getItem()));
                                if (exp > 0 && world instanceof ServerWorld) {
                                    block.dropXpOnBlockBreak((ServerWorld) world, position, exp);
                                }
                            }
                        }
                    }
                } else {
                    breakBlock(world, position, true, miner, stack);
                }
            });
    }

    public static void cascadingBreakBlocks(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, INamedTag<Block> blockTag, int blockLimit, int searchLimit) {
        if (state.getBlock().isIn(blockTag)) {
            if (miner instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) miner;
                if (player instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                    List<BlockPos> found = findPositions(state, pos, world, blockLimit, searchLimit);
                    for (BlockPos foundPos : found) {
                        if (pos.equals(foundPos)) {
                            continue;
                        }
                        BlockState foundState = world.getBlockState(foundPos);
                        int exp = ForgeHooks.onBlockBreakEvent(world, serverPlayer.interactionManager.getGameType(), serverPlayer, foundPos);
                        if (exp == -1) {
                            // If we can't actually break the block continue (this allows mods to stop us from vein mining into protected land)
                            continue;
                        }
                        Block block = foundState.getBlock();
                        TileEntity tile = getTileEntity(world, pos);
                        boolean removed = foundState.removedByPlayer(world, foundPos, player, true, world.getFluidState(foundPos));
                        if (removed) {
                            block.onPlayerDestroy(world, foundPos, foundState);
                            block.harvestBlock(world, player, foundPos, foundState, tile, stack);
                            player.addStat(Stats.ITEM_USED.get(stack.getItem()));
                            if (exp > 0 && world instanceof ServerWorld) {
                                block.dropXpOnBlockBreak((ServerWorld) world, foundPos, exp);
                            }
                        }
                    }
                }
            } else {
                List<BlockPos> found = findPositions(state, pos, world, blockLimit, searchLimit);
                for (BlockPos foundPos : found) {
                    if (!pos.equals(foundPos)) {
                        breakBlock(world, foundPos, true, miner, stack);
                    }
                }
            }
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
        if (world == null || !world.isBlockPresent(pos)) {
            return null;
        }
        return world.getTileEntity(pos);
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

    public static List<BlockPos> findPositions(BlockState state, BlockPos origin, World world, int maxBlocks, int maxRange) {
        List<BlockPos> found = new ArrayList<>();
        Set<BlockPos> checked = new ObjectOpenHashSet<>();
        found.add(origin);
        Block startBlock = state.getBlock();
        int maxCount = maxBlocks - 1;
        for (int i = 0; i < found.size(); i++) {
            BlockPos blockPos = found.get(i);
            checked.add(blockPos);
            for (BlockPos pos : BlockPos.getAllInBoxMutable(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1))) {
                if (!checked.contains(pos)) {
                    if (maxRange == -1 || Math.sqrt(origin.distanceSq(pos)) <= maxRange) {
                        if (world.isBlockPresent(pos) && startBlock == world.getBlockState(pos).getBlock()) {
                            found.add(pos.toImmutable());
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

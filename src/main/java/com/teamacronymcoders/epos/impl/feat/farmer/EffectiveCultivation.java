package com.teamacronymcoders.epos.impl.feat.farmer;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.ibm.icu.impl.Pair;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposUtil;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BoneMealItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.stream.Stream;

// Increased Crop Yield (GML?) + Chance to get Bone-Meal to apply to a larger area (25% 3x3, 10% 5x5, 5% 7x7)
public class EffectiveCultivation {

    public static void registerFeatManagers() {
        boneMealManager.subscribe();
    }

    private static final EventManager.ISubscribe boneMealManager = EventManager.create(PlayerInteractEvent.RightClickBlock.class, EventManager.Bus.FORGE)
            .filter(event -> !event.getWorld().isClientSide && EposCharacterUtil.hasFeat(event.getEntityLiving(), EposFeatIds.EFFECTIVE_CULTIVATION) && event.getItemStack().getItem() instanceof BoneMealItem && event.getUseItem().equals(Event.Result.ALLOW) && event.getWorld().getBlockState(event.getHitVec().getBlockPos()).getBlock() instanceof IGrowable)
            .process(event -> {
               World world = event.getWorld();
               BlockPos pos = event.getHitVec().getBlockPos();
               if (EposUtil.getRandomizedChance(0.05F)) {
                   // 7x7
                   handleBoneMealing(pos, world, BlockPos.withinManhattanStream(pos, 7, 0, 7));
               } else if (EposUtil.getRandomizedChance(0.1F)) {
                   // 5x5
                   handleBoneMealing(pos, world, BlockPos.withinManhattanStream(pos, 5, 0, 5));
               } else if (EposUtil.getRandomizedChance(0.25F)) {
                   // 3x3
                   handleBoneMealing(pos, world, BlockPos.withinManhattanStream(pos, 3, 0, 3));
               }
            });

    private static void handleBoneMealing(BlockPos origin, World world, Stream<BlockPos> stream) {
        stream.filter(rawPos -> rawPos != origin)
                .map(filteredPos -> Pair.of(filteredPos, world.getBlockState(filteredPos)))
                .filter(rawPair -> rawPair.second.getBlock() instanceof IGrowable)
                .map(filteredState -> Pair.of((IGrowable) filteredState.second.getBlock(), filteredState))
                .filter(growableBlockStatePair -> growableBlockStatePair.first.isValidBonemealTarget(
                        world,
                        growableBlockStatePair.second.first,
                        growableBlockStatePair.second.second, world.isClientSide))
                .forEach(growableBlockStatePair -> {
                    if (!world.isClientSide) {
                        growableBlockStatePair.first.performBonemeal(
                                (ServerWorld) world,
                                world.random,
                                growableBlockStatePair.second.first,
                                growableBlockStatePair.second.second);
                    }
                });
    }
}

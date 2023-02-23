package com.teamacronymcoders.epos.impl.feat.farmer;

import com.ibm.icu.impl.Pair;
import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.stream.Stream;

// Increased Crop Yield (GML?) + Chance to get Bone-Meal to apply to a larger area (25% 3x3, 10% 5x5, 5% 7x7)
public class EffectiveCultivation {

    public static void registerFeatManagers() {
        boneMealManager.subscribe();
    }

    private static final EventManager.ISubscribe boneMealManager = EventManager.create(PlayerInteractEvent.RightClickBlock.class, EventManager.Bus.FORGE)
            .filter(event -> !event.getWorld().isClientSide && EposCharacterUtil.hasFeat(event.getEntityLiving(), EposFeatIds.EFFECTIVE_CULTIVATION) && event.getItemStack().getItem() instanceof BoneMealItem && event.getUseItem().equals(Event.Result.ALLOW) && event.getWorld().getBlockState(event.getHitVec().getBlockPos()).getBlock() instanceof BonemealableBlock)
            .process(event -> {
               Level world = event.getWorld();
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

    private static void handleBoneMealing(BlockPos origin, Level level, Stream<BlockPos> stream) {
        stream.filter(rawPos -> rawPos != origin)
                .map(filteredPos -> Pair.of(filteredPos, level.getBlockState(filteredPos)))
                .filter(rawPair -> rawPair.second.getBlock() instanceof BonemealableBlock)
                .map(filteredState -> Pair.of((BonemealableBlock) filteredState.second.getBlock(), filteredState))
                .filter(growableBlockStatePair -> growableBlockStatePair.first.isValidBonemealTarget(
                        level,
                        growableBlockStatePair.second.first,
                        growableBlockStatePair.second.second, level.isClientSide))
                .forEach(growableBlockStatePair -> {
                    if (!level.isClientSide) {
                        growableBlockStatePair.first.performBonemeal(
                                (ServerLevel) level,
                                level.random,
                                growableBlockStatePair.second.first,
                                growableBlockStatePair.second.second);
                    }
                });
    }
}

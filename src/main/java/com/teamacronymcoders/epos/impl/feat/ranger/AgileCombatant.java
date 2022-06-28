package com.teamacronymcoders.epos.impl.feat.ranger;

import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Arrays;

public class AgileCombatant {
    public static void registerFeatManagers() {
        dodgeManager.subscribe();
        fenceJumpManager.subscribe();
    }

    private static final EventManager.ISubscribe dodgeManager = EventManager.create(LivingDamageEvent.class, EventManager.Bus.FORGE)
            .filter(event -> !event.getEntity().level.isClientSide && EposCharacterUtil.hasFeat(event.getEntityLiving(), EposFeatIds.AGILE_COMBATANT))
            .process(event -> {
                if (EposUtil.getRandomizedChance(0.04f)) {
                    event.setCanceled(true);
                }
                if (event.getSource().getMsgId().equals("fall")) {
                    event.setAmount(event.getAmount() / 2);
                }
            });

    private static final EventManager.ISubscribe fenceJumpManager = EventManager.create(LivingEvent.LivingJumpEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                if (event.getEntityLiving() instanceof LocalPlayer) {
                    LocalPlayer player = (LocalPlayer) event.getEntityLiving();
                    if (player.level instanceof ClientLevel) {
                        return EposCharacterUtil.hasFeat(event.getEntityLiving(), EposFeatIds.AGILE_COMBATANT) && isNearbyFence((ClientLevel) player.level, player);
                    }
                }
                return false;
            })
            .process(event -> {
                LocalPlayer player = (LocalPlayer) event.getEntityLiving();
                if (player.input.jumping) {
                    player.setDeltaMovement(player.getDeltaMovement().add(0.0D, 0.05D, 0.0D));
                }
            });

    private static boolean isNearbyFence(ClientLevel level, LocalPlayer player) {
        int x = (int) player.getX() - 1;
        int y = (int) player.getY();
        int z = (int) player.getZ() - 1;

        for(int expandX = 0; expandX < 3; expandX++) {
            for(int expandZ = 0; expandZ < 3; expandZ++) {
                if (expandX != x || expandZ != z) {
                    BlockState state = level.getBlockState(new BlockPos(x + expandX, y, z + expandZ));
                    Block block = state.getBlock();
                    if (block instanceof FenceBlock || block instanceof WallBlock || hasAnyTags(state, Tags.Blocks.FENCES, Tags.Blocks.FENCE_GATES, BlockTags.WALLS)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean hasAnyTags(BlockState state, TagKey<Block>... tags) {
        return Arrays.stream(tags).anyMatch(state::is);
    }
}

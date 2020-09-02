package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import com.teamacronymcoders.epos.util.helpers.BlockBreakHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;

public class OreExtractionFeat {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "ore_extraction");
    // TODO: Rewrite this to work similarly to Essence Code
    public static final Feat FEAT =
        FeatBuilder.start(NAME)
            .withEventHandler(BlockEvent.BreakEvent.class,
                (breakEvent, entity, iCharacterStats) -> {
                    if (entity.getActiveItemStack().getToolTypes().contains(ToolType.PICKAXE)) {
                        if (breakEvent.getState().getBlock().isIn(Blocks.ORES)) {
                            BlockPos pos = breakEvent.getPos();
                            IWorld iWorld = breakEvent.getWorld();
                            if (iWorld instanceof World) {
                                World world = (World) iWorld;
                                PlayerEntity player = breakEvent.getPlayer();
                                BlockBreakHelper.cascadingBreakBlocks(player.getHeldItem(player.getActiveHand()), world, world.getBlockState(pos), pos, player, Blocks.ORES, 125, 25);
                            }
                        }
                    }
                })
            .finish();
}

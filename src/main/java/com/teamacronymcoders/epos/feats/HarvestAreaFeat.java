package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import com.teamacronymcoders.epos.utils.helpers.BlockBreakHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;

public class HarvestAreaFeat {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "area_expansion");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                .withEventHandler(BlockEvent.BreakEvent.class,
                        (breakEvent, entity, iCharacterStats) -> {
                            if (entity.getActiveItemStack().getToolTypes().contains(ToolType.PICKAXE)) {
                                PlayerEntity player = breakEvent.getPlayer();
                                BlockPos pos = breakEvent.getPos();
                                World world = breakEvent.getWorld().getWorld();
                                int skillLevel = iCharacterStats.getSkills().getSkillLevel(NAME);
                                Iterable<BlockPos> positions = null;
                                if (skillLevel == 1) {
                                    positions = BlockPos.getAllInBoxMutable(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX(), pos.getY() - 1, pos.getZ());
                                } else if (skillLevel == 2) {
                                    positions = BlockPos.getAllInBoxMutable(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1, pos.getX() - 1, pos.getY() - 1, pos.getZ() -1);
                                }
                                if (positions != null) {
                                    BlockBreakHelper.handleBreakBlock(positions, world, player);
                                }
                            }
                        })
                .finish();
}

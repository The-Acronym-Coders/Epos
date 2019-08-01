package com.teamacronymcoders.eposmajorum.content.utility.mining;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import com.teamacronymcoders.eposmajorum.utils.UtilMethods;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.world.BlockEvent;

public class HarvestAreaSkill {
    private static final ResourceLocation RL = new ResourceLocation(EposAPI.ID, "area_expansion");
    public static final Feat FEAT = FeatBuilder.start(RL)
            .withEventHandler(BlockEvent.BreakEvent.class,
                    (breakEvent, entity, iCharacterStats) -> {
                        if (entity.getActiveItemStack().getToolTypes().contains(ToolType.PICKAXE)) {
                            PlayerEntity player = breakEvent.getPlayer();
                            BlockPos pos = breakEvent.getPos();
                            World world = breakEvent.getWorld().getWorld();
                            int skillLevel = iCharacterStats.getSkills().getOrCreate(RL.toString()).getLevel();
                            if (skillLevel == 1) {
                                for (BlockPos pos1 : BlockPos.getAllInBoxMutable(
                                        pos.getX(), pos.getY() + 1, pos.getZ(),
                                        pos.getX(), pos.getY() - 1, pos.getZ()
                                )) {
                                    BlockState state = world.getBlockState(pos1);
                                    UtilMethods.handleBreakBlock(world, pos1, state, player, true, null);
                                }
                            } else if (skillLevel == 2) {
                                for (BlockPos pos1 : BlockPos.getAllInBoxMutable(
                                        pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1,
                                        pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1
                                )) {
                                    BlockState state = world.getBlockState(pos1);
                                    UtilMethods.handleBreakBlock(world, pos1, state, player, true, null);
                                }
                            }
                        }
                    })
            .finish();
}

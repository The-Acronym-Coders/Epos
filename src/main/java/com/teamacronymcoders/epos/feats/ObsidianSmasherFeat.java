package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ObsidianSmasherFeat {
    public static final ResourceLocation TOOL_NAME = new ResourceLocation(EposAPI.ID, "obsidian_smasher");
    public static final ResourceLocation NO_TOOL_NAME = new ResourceLocation(EposAPI.ID, "obsidian_smasher_no_tool");

    public static final Feat TOOL_FEAT =
            FeatBuilder.start(TOOL_NAME)
                    .withEventHandler(PlayerEvent.BreakSpeed.class, (breakSpeed, livingEntity, iCharacterStats) -> {
                        PlayerEntity playerEntity = breakSpeed.getEntityPlayer();
                        if (breakSpeed.getState().getBlock() == Blocks.OBSIDIAN && playerEntity.getHeldItemMainhand().getHarvestLevel(ToolType.PICKAXE, playerEntity, breakSpeed.getState()) >= ItemTier.DIAMOND.getHarvestLevel()) {
                            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed() * 10);
                        }
                    }).finish();

    public static final Feat NO_TOOL_FEAT =
            FeatBuilder.start(NO_TOOL_NAME)
                    .withEventHandler(PlayerEvent.BreakSpeed.class, (breakSpeed, livingEntity, iCharacterStats) -> {
                        PlayerEntity playerEntity = breakSpeed.getEntityPlayer();
                        if (breakSpeed.getState().getBlock() == Blocks.OBSIDIAN && playerEntity.getHeldItemMainhand().isEmpty()) {
                            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed() * 10);
                        }
                    }).finish();
}

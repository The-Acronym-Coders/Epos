package com.teamacronymcoders.eposmajorum.content;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ObsidianSmasher {
    public static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "obsidian_smasher");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                .withEventHandler(PlayerEvent.BreakSpeed.class, (breakSpeed, livingEntity, iCharacterStats) -> {
                    PlayerEntity playerEntity = breakSpeed.getEntityPlayer();
                    if (!breakSpeed.isCanceled() &&
                            breakSpeed.getState().getBlock() == Blocks.OBSIDIAN &&
                            playerEntity.getHeldItemMainhand().getHarvestLevel(ToolType.PICKAXE, playerEntity, breakSpeed.getState()) >= ItemTier.DIAMOND.getHarvestLevel()) {
                        if (breakSpeed.getOriginalSpeed() == breakSpeed.getNewSpeed()) {
                            breakSpeed.setNewSpeed(breakSpeed.getOriginalSpeed() * 10);
                        } else {
                            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed() * 10);
                        }
                    }
                }).finish();
}

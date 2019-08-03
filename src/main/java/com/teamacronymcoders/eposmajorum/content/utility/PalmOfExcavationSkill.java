package com.teamacronymcoders.eposmajorum.content.utility;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PalmOfExcavationSkill {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "palm_of_excavation");

    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(PlayerEvent.BreakSpeed.class,
                            ((breakSpeed, entity, iCharacterStats) -> {
                                int level = iCharacterStats.getSkills().get(NAME.toString()) != null ? iCharacterStats.getSkills().get(NAME.toString()).getLevel() : 0;
                                float oldSpeed = Math.min(breakSpeed.getOriginalSpeed(), 1);
                                ToolType toolType = breakSpeed.getState().getHarvestTool();
                                if ("pickaxe".equals(toolType.getName())) {
                                    breakSpeed.setNewSpeed((((level * 25f) / 100f) * oldSpeed) + breakSpeed.getNewSpeed());
                                } else {
                                    breakSpeed.setNewSpeed((((level * 50f) / 100f) * oldSpeed) + breakSpeed.getNewSpeed());
                                }

                            }))
                    .withEventHandler(PlayerEvent.HarvestCheck.class,
                            ((harvestCheck, entity, iCharacterStats) -> {
                                int level = iCharacterStats.getSkills().get(NAME.toString()) != null ? iCharacterStats.getSkills().get(NAME.toString()).getLevel() : 0;
                                BlockState state = harvestCheck.getTargetBlock();
                                ToolType toolType = state.getHarvestTool();

                                if (toolType != null && (level >= state.getHarvestLevel() &&
                                        ("pickaxe".equals(toolType.getName()) || "axe".equals(toolType.getName()) || "shovel".equals(toolType.getName())))) {
                                    harvestCheck.setCanHarvest(true);
                                }
                            }))
                    .withEventHandler(FeatAcquiredEvent.class,
                            (featAcquiredEvent, entity, iCharacterStats) -> {
                                if (featAcquiredEvent.getFeatAcquired().getRegistryName().compareTo(NAME) == 0) {
                                    iCharacterStats.getSkills().putSkill(NAME);
                                }
                            }).finish();
}
package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.utils.helpers.SkillInfoHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PalmOfExcavationFeat {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "palm_of_excavation");

    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(PlayerEvent.BreakSpeed.class,
                            ((breakSpeed, entity, iCharacterStats) -> {
                                int level = SkillInfoHelper.getSkillLevel(NAME, iCharacterStats);
                                float oldSpeed = Math.min(breakSpeed.getOriginalSpeed(), 1);
                                ToolType toolType = breakSpeed.getState().getHarvestTool();
                                if ("pickaxe".equals(toolType.getName())) {
                                    breakSpeed.setNewSpeed((((level * 50f) / 100f) * oldSpeed) + breakSpeed.getNewSpeed());
                                } else {
                                    breakSpeed.setNewSpeed((((level * 15f) / 100f) * oldSpeed) + breakSpeed.getNewSpeed());
                                }
                            }))
                    .withEventHandler(PlayerEvent.HarvestCheck.class,
                            ((harvestCheck, entity, iCharacterStats) -> {
                                int level = SkillInfoHelper.getSkillLevel(NAME, iCharacterStats);
                                BlockState state = harvestCheck.getTargetBlock();
                                ToolType toolType = state.getHarvestTool();
                                if (toolType != null && level >= state.getHarvestLevel()) {
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
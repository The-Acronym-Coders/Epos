package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EfficiencyFeats {
    private static final ResourceLocation MINER = new ResourceLocation(EposAPI.ID, "miners_efficiency");
    private static final ResourceLocation LUMBERJACK = new ResourceLocation(EposAPI.ID, "lumberjacks_efficiency");

    public static final Feat MINER_FEAT =
            FeatBuilder.start(MINER)
                    .withEventHandler(PlayerEvent.BreakSpeed.class,
                            (breakSpeed, entity, iCharacterStats) -> {
                                if (entity.getActiveItemStack().getToolTypes().contains(ToolType.PICKAXE)) {
                                    float speed = breakSpeed.getNewSpeed();
                                    SkillInfo skill = iCharacterStats.getSkills().get(MINER);
                                    int level = skill == null ? 0 : skill.getLevel();
                                    breakSpeed.setNewSpeed(speed + (0.4f * level));
                                }
                            }
                    )
                    .withEventHandler(FeatAcquiredEvent.class,
                            (featAcquiredEvent, entity, iCharacterStats) -> {
                                if (featAcquiredEvent.getFeatAcquired().getRegistryName().compareTo(MINER) == 0) {
                                    iCharacterStats.getSkills().putSkill(MINER);
                                }
                            }
                    ).finish();

    public static final Feat LUMBERJACK_FEAT =
            FeatBuilder.start(LUMBERJACK)
                    .withEventHandler(PlayerEvent.BreakSpeed.class,
                            (breakSpeed, entity, iCharacterStats) -> {
                                if (entity.getActiveItemStack().getToolTypes().contains(ToolType.AXE)) {
                                    float speed = breakSpeed.getNewSpeed();
                                    SkillInfo skill = iCharacterStats.getSkills().get(LUMBERJACK);
                                    int level = skill == null ? 0 : skill.getLevel();
                                    breakSpeed.setNewSpeed(speed + (0.4f * level));
                                }
                            }
                    )
                    .withEventHandler(FeatAcquiredEvent.class,
                            (featAcquiredEvent, entity, iCharacterStats) -> {
                                if (featAcquiredEvent.getFeatAcquired().getRegistryName().compareTo(LUMBERJACK) == 0) {
                                    iCharacterStats.getSkills().putSkill(LUMBERJACK);
                                }
                            }
                    ).finish();
}

package com.teamacronymcoders.eposmajorum.content.utility.mining;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import com.teamacronymcoders.eposmajorum.api.skill.SkillInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MinersEfficiencySkill {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "miners_efficiency");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                .withEventHandler(PlayerEvent.BreakSpeed.class,
                        (breakSpeed, entity, iCharacterStats) -> {
                            if (entity.getActiveItemStack().getToolTypes().contains(ToolType.PICKAXE)) {
                                float speed = breakSpeed.getNewSpeed();
                                SkillInfo skill = iCharacterStats.getSkills().get(NAME.toString());
                                int level = skill == null ? 0 : skill.getLevel();
                                breakSpeed.setNewSpeed(speed + (0.4f * level));
                            }
                        }
                    )
                .withEventHandler(FeatAcquiredEvent.class,
                        (featAcquiredEvent, entity, iCharacterStats) -> {
                            if (featAcquiredEvent.getFeatAcquired().getRegistryName().compareTo(NAME) == 0) {
                                iCharacterStats.getSkills().putSkill(NAME);
                            }
                        }
                    ).finish();
}

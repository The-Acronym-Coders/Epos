package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.event.AltLivingDamageEvent;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import java.util.Set;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public class ImprovisedCombatFeat {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "improvised_combat");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(AltLivingDamageEvent.class,
                            (altLivingDamageEvent, entity, iCharacterStats) -> {
                                Set<ToolType> toolTypes = entity.getActiveItemStack().getToolTypes();
                                if (toolTypes.contains(ToolType.PICKAXE) || toolTypes.contains(ToolType.AXE) || toolTypes.contains(ToolType.SHOVEL)) {
                                    int skillLevel = iCharacterStats.getSkills().getLevel(NAME);
                                    altLivingDamageEvent.setAmount(altLivingDamageEvent.getAmount() * 1.25F + (0.01F * skillLevel));
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

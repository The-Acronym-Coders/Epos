package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.event.AltLivingDamageEvent;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;
import java.util.Set;

public class ImprovisedCombatFeat {
    public static final ArrayList<Item> VALID_ITEMS = new ArrayList<>();
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

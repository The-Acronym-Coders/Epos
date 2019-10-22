package com.teamacronymcoders.epos.feat;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.event.AltLivingDamageEvent;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public class WayOfTheBladeFeat {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "way_of_the_blade");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(AltLivingDamageEvent.class,
                            (altLivingDamageEvent, entity, iCharacterStats) -> {
                                if (entity.getActiveItemStack().getItem() instanceof SwordItem) {
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

package com.teamacronymcoders.eposmajorum.content.skills.offence;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.event.AltLivingDamageEvent;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public class WayOfTheBladeSkill {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "way_of_the_blade");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(AltLivingDamageEvent.class,
                            (altLivingDamageEvent, entity, iCharacterStats) -> {
                                if (entity.getActiveItemStack().getItem() instanceof SwordItem) {
                                    int skillLevel = iCharacterStats.getSkills().getOrCreate(NAME.toString()).getLevel();
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

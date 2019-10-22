package com.teamacronymcoders.epos.feat;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.event.AltLivingDamageEvent;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import net.minecraft.util.ResourceLocation;

public class UnarmedStrikeFeat {
    public static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "unarmed_strike");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(AltLivingDamageEvent.class,
                            (livingDamageEvent, character, characterStats) -> {
                                if (character.getActiveItemStack().isEmpty()) {
                                    int skillLevel = characterStats.getSkills()
                                            .getOrCreate(NAME.toString())
                                            .getLevel();
                                    livingDamageEvent.setAmount(livingDamageEvent.getAmount() *
                                            (1.25F + (0.01F * skillLevel)));
                                }
                            })
                    .withEventHandler(FeatAcquiredEvent.class,
                            ((featAcquiredEvent, character, characterStats) -> {
                                if (featAcquiredEvent.getFeatAcquired().getRegistryName()
                                        .compareTo(NAME) == 0) {
                                    characterStats.getSkills().putSkill(NAME);
                                }
                            }))
                    .finish();
}

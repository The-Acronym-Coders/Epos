package com.teamacronymcoders.eposmajorum.content;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.event.AltLivingDamageEvent;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.util.ResourceLocation;

public class UnarmedStrike {
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

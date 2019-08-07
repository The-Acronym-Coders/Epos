package com.teamacronymcoders.eposmajorum.content;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LuckyLure {
    public static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "lucky_lure");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                .withEventHandler(TickEvent.PlayerTickEvent.class, ((event, livingEntity, iCharacterStats) -> {
                    if (event.player.fishingBobber != null) {
                        event.player.addPotionEffect(new EffectInstance(Effects.LUCK, 10, 0, true, true));
                    }
                })).finish();
}

package com.teamacronymcoders.epos.feat;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TickEvent;

public class PurityFeats {
    private static final ResourceLocation PURITY_NAME = new ResourceLocation(EposAPI.ID, "purity_of_body");
    public static final Feat PURITY =
            FeatBuilder.start(PURITY_NAME)
                    .withEventHandler(TickEvent.PlayerTickEvent.class,
                            ((playerTickEvent, entity, iCharacterStats) -> {
                                removeEffect(entity, Effects.POISON, 1);
                                removeEffect(entity, Effects.NAUSEA, -1);
                                removeEffect(entity, Effects.HUNGER, -1);
                                removeEffect(entity, Effects.MINING_FATIGUE, -1);
                                removeEffect(entity, Effects.SLOWNESS, -1);
                            })).finish();

    private static final ResourceLocation DIAMOND_NAME = new ResourceLocation(EposAPI.ID, "diamond_body");
    public static final Feat DIAMOND =
            FeatBuilder.start(DIAMOND_NAME)
                    .withEventHandler(TickEvent.PlayerTickEvent.class,
                            ((playerTickEvent, entity, iCharacterStats) -> {
                                removeEffect(entity, Effects.POISON, -1);
                                removeEffect(entity, Effects.WITHER, 2);
                            })).finish();

    private static void removeEffect(LivingEntity entity, Effect effect, int maxLevel) {
        EffectInstance effectInstance = entity.getActivePotionEffect(effect);
        if (effectInstance != null && (maxLevel == -1 || effectInstance.getAmplifier() <= maxLevel)) {
            entity.removePotionEffect(effect);
        }
    }

}

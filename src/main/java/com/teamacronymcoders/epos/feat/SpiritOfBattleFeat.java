package com.teamacronymcoders.epos.feat;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class SpiritOfBattleFeat {
    public static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "spirit_of_battle");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(LivingDeathEvent.class,
                            (livingDeathEvent, livingEntity, iCharacterStats) -> {
                                if (livingDeathEvent.getEntity() instanceof IMob && livingDeathEvent.getSource().getTrueSource() instanceof PlayerEntity) {
                                    ((PlayerEntity) livingDeathEvent.getSource().getTrueSource()).addPotionEffect(new EffectInstance(Effects.STRENGTH, 120, 0));
                                }
                            }).finish();
}

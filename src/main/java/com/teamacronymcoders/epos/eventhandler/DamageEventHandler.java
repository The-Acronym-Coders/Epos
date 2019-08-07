package com.teamacronymcoders.epos.eventhandler;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.EposCapabilities;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.event.AltLivingDamageEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = EposAPI.ID)
public class DamageEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onAttackedEntity(LivingDamageEvent livingDamageEvent) {
        if (livingDamageEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity character = (LivingEntity) livingDamageEvent.getSource().getTrueSource();
            LazyOptional<ICharacterStats> stats = character
                    .getCapability(EposCapabilities.CHARACTER_STATS);
            stats.ifPresent(iCharacterStats -> iCharacterStats.getFeats()
                    .handleEvent(new AltLivingDamageEvent(livingDamageEvent), character, iCharacterStats));
        }
    }
}

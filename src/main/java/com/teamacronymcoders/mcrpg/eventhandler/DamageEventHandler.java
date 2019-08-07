package com.teamacronymcoders.mcrpg.eventhandler;

import com.teamacronymcoders.mcrpg.api.MCRPGAPI;
import com.teamacronymcoders.mcrpg.api.MCRPGCapabilities;
import com.teamacronymcoders.mcrpg.api.characterstats.ICharacterStats;
import com.teamacronymcoders.mcrpg.api.event.AltLivingDamageEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = MCRPGAPI.ID)
public class DamageEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onAttackedEntity(LivingDamageEvent livingDamageEvent) {
        if (livingDamageEvent.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity character = (LivingEntity) livingDamageEvent.getSource().getTrueSource();
            LazyOptional<ICharacterStats> stats = character
                    .getCapability(MCRPGCapabilities.CHARACTER_STATS);
            stats.ifPresent(iCharacterStats -> iCharacterStats.getFeats()
                    .handleEvent(new AltLivingDamageEvent(livingDamageEvent), character, iCharacterStats));
        }
    }
}

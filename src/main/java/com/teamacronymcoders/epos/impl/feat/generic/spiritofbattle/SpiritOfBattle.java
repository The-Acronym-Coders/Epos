package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class SpiritOfBattle {
    public static final EventManager.ISubscribe featManager = EventManager.create(LivingDamageEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                Entity dealer = event.getSource().getEntity();
                if (dealer instanceof LivingEntity) {
                    LivingEntity character = (LivingEntity) dealer;
                    return EposCharacterUtil.hasFeat(character, EposFeatIds.SPIRIT_OF_BATTLE);
                }
                return false;
            })
            .process(event -> {
                Entity dealer = event.getSource().getEntity();
                if (dealer instanceof LivingEntity && event.getEntity() != null) {
                    LivingEntity character = (LivingEntity) dealer;
                    EntityType<?> entityType = event.getEntity().getType();
                    EposRegistries registries = Epos.instance().getRegistries();
                    ISpiritualAid aid = registries.getSpiritualAid(entityType.getRegistryName());
                    if (aid != null) {
                        for (EffectInstance instance : aid.getEffects()) {
                            EffectInstance currentInstance = character.getEffect(instance.getEffect());
                            if (currentInstance != null && currentInstance.getAmplifier() <= instance.getAmplifier()) {
                                character.removeEffect(instance.getEffect());
                                character.addEffect(instance);
                            }
                        }
                    }
                }
            });
}

package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.registries.ForgeRegistries;

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
                    ResourceLocation entityId = ForgeRegistries.ENTITIES.getKey(entityType);
                    EposRegistries registries = Epos.instance().getRegistries();
                    if (entityId != null) {
                      ISpiritualAid aid = registries.getSpiritualAid(entityId);
                      if (aid != null) {
                        for (MobEffectInstance instance : aid.getEffects()) {
                          MobEffectInstance currentInstance = character.getEffect(instance.getEffect());
                          if (currentInstance != null && currentInstance.getAmplifier() <= instance.getAmplifier()) {
                            character.removeEffect(instance.getEffect());
                            character.addEffect(instance);
                          }
                        }
                      }
                    }
                }
            });
}

package com.teamacronymcoders.epos.impl.feat.monk;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;

// TODO: Rework to not read and write to NBT every Tick
public class EmbraceOfTheLotus {
    public static void registerFeatManagers() {
        featManager.subscribe();
        featInfoManager.subscribe();
    }

    private static final String REMAINING_TIME = "eotl_remaining";

    public static final EventManager.ISubscribe featManager = EventManager.create(TickEvent.PlayerTickEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                if (event.player != null) return EposCharacterUtil.hasFeat(event.player, EposFeatIds.EMBRACE_OF_THE_LOTUS);
                return false;
            })
            .process(event -> {
                PlayerEntity player = event.player;
                CompoundNBT persistent = player.getPersistentData();
                float absorptionAmount = player.getAbsorptionAmount();
                boolean hasLessThanMaxAbsorption = absorptionAmount < 5f;
                if (persistent.contains(REMAINING_TIME)) {
                    int remainingTime = persistent.getInt(REMAINING_TIME);
                    if (remainingTime <= 0 && hasLessThanMaxAbsorption) {
                        player.setAbsorptionAmount(absorptionAmount + 0.5f);
                        persistent.putInt(REMAINING_TIME, 20);
                    } else {
                        if (hasLessThanMaxAbsorption) {
                            remainingTime--;
                            persistent.putInt(REMAINING_TIME, remainingTime);
                        }
                    }
                } else {
                    if (hasLessThanMaxAbsorption) {
                        player.setAbsorptionAmount(absorptionAmount + 0.5f);
                    }
                    persistent.putInt(REMAINING_TIME, 20);
                }
            });

    private static final EventManager.ISubscribe featInfoManager = EventManager.createGeneric(RegistryEvent.Register.class, EventManager.Bus.MOD, FeatInfo.class)
            .process(event -> {

            });


}

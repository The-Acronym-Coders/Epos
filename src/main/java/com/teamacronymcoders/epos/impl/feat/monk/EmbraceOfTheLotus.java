package com.teamacronymcoders.epos.impl.feat.monk;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.impl.featinfo.EOTLFeatInfo;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;

// TODO: Rework to not read and write to NBT every Tick
public class EmbraceOfTheLotus {

    public static void registerFeatManagers() {
        featManager.subscribe();
        featInfoManager.subscribe();
    }

    public static final EventManager.ISubscribe featManager = EventManager.create(TickEvent.PlayerTickEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                if (event.player != null) return EposCharacterUtil.hasFeat(event.player, EposFeatIds.EMBRACE_OF_THE_LOTUS);
                return false;
            })
            .process(event -> {
                PlayerEntity player = event.player;
                float absorptionAmount = player.getAbsorptionAmount();
                boolean hasLessThanMaxAbsorption = absorptionAmount < 5f;
                FeatInfo info = EposCharacterUtil.getFeatInfo(player, EposFeatIds.EMBRACE_OF_THE_LOTUS);
                if (info instanceof EOTLFeatInfo) {
                    EOTLFeatInfo eotlInfo = (EOTLFeatInfo) info;
                    int remainingTime = eotlInfo.getRemainingTime();
                    if (remainingTime <= 0 && hasLessThanMaxAbsorption) {
                        player.setAbsorptionAmount(absorptionAmount + 0.5f);
                        eotlInfo.setRemainingTime(20);
                    } else {
                        if (hasLessThanMaxAbsorption) {
                            eotlInfo.decrementRemainingTime();
                        }
                    }
                }
            });

  private static final EventManager.ISubscribe featInfoManager = EventManager.modGeneric(RegistryEvent.Register.class, FeatInfo.class)
            .process(event -> {
                ((RegistryEvent.Register) event).getRegistry().register(new EOTLFeatInfo().setRegistryName(EposFeatIds.EMBRACE_OF_THE_LOTUS));
            });


}

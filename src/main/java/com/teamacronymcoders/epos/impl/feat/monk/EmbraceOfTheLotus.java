package com.teamacronymcoders.epos.impl.feat.monk;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraftforge.event.TickEvent;

public class EmbraceOfTheLotus {

    public static final EventManager.ISubscribe featManager = EventManager.create(TickEvent.PlayerTickEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                if (event.player != null) return EposCharacterUtil.hasFeat(event.player, EposFeatIds.EMBRACE_OF_THE_LOTUS);
                return false;
            })
            .process(event -> {
                event.player.setAbsorptionAmount(2.5f);
            });
}

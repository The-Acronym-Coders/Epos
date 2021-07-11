package com.teamacronymcoders.epos.impl.feat.generic;

import com.hrznstudio.titanium.event.handler.EventManager;
import net.minecraftforge.event.entity.player.PlayerEvent;

// Increased Mining Speed
public class EffectiveMining {
    public static final EventManager.ISubscribe featManager = EventManager.create(PlayerEvent.BreakSpeed.class, EventManager.Bus.FORGE)
            .filter(event -> {

                return false;
            })
            .process(event -> {

            });
}

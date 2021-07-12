package com.teamacronymcoders.epos.impl.feat.generic;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposUtil;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class AgileCombatant {
    public static final EventManager.ISubscribe featManager = EventManager.create(LivingDamageEvent.class, EventManager.Bus.FORGE)
            .filter(event -> EposCharacterUtil.hasFeat(event.getEntityLiving(), EposFeatIds.AGILE_COMBATANT))
            .process(event -> {
                if (EposUtil.getRandomizedChance(0.04f)) {
                    event.setCanceled(true);
                }
            });
}

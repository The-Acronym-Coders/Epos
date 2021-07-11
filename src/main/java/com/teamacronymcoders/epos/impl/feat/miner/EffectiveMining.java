package com.teamacronymcoders.epos.impl.feat.miner;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EffectiveMining {
    public static final EventManager.ISubscribe featManager = EventManager.create(PlayerEvent.BreakSpeed.class, EventManager.Bus.FORGE)
            .filter(event -> EposCharacterUtil.hasFeat(event.getPlayer(), EposFeatIds.EFFECTIVE_MINING))
            .process(event -> {
                PlayerEntity player = event.getPlayer();
                SkillInfo info = EposCharacterUtil.getSkillInfo(player, EposFeatIds.EFFECTIVE_MINING);
                if (info != null) {
                    event.setNewSpeed(event.getOriginalSpeed() + (1.45f / info.getMaxLevel()) * info.getLevel());
                }
            });
}

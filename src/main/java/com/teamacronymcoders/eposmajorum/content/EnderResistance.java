package com.teamacronymcoders.eposmajorum.content;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EnderResistance {
    public static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "ender_resistance");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                .withEventHandler(EnderTeleportEvent.class, (enderTeleportEvent, livingEntity, iCharacterStats) -> {
                    if (!enderTeleportEvent.isCanceled()) {
                        enderTeleportEvent.setAttackDamage(0);
                    }
                }).finish();
}

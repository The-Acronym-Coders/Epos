package com.teamacronymcoders.epos.utils.helpers;

import net.minecraft.entity.player.PlayerEntity;

public class PlayerHelper {
    public static boolean eventChecks(PlayerEntity playerEntity) {
        return playerEntity != null && !playerEntity.isCreative() && !playerEntity.isSpectator();
    }
}

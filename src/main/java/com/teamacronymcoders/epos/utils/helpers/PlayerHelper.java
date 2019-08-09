package com.teamacronymcoders.epos.utils.helpers;

import net.minecraft.entity.player.PlayerEntity;

public class PlayerHelper {
    public static boolean eventChecks(PlayerEntity playerEntity) {
        return isNotNull(playerEntity) && !isCreative(playerEntity) && !isSpectator(playerEntity);
    }

    private static boolean isNotNull(PlayerEntity playerEntity) {
        return playerEntity != null;
    }

    private static boolean isCreative(PlayerEntity playerEntity) {
        return playerEntity.isCreative();
    }

    private static boolean isSpectator(PlayerEntity playerEntity) {
        return playerEntity.isSpectator();
    }
}

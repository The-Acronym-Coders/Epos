package com.teamacronymcoders.eposmajorum.utils;

import com.teamacronymcoders.eposmajorum.api.sounds.SoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;

public class SoundUtils {
    public static void playEntitySound(SoundEvent event, LivingEntity entity) {
        entity.playSound(event, 1f, 1f);
    }

    public static void playLevelUpSound(LivingEntity entity) {
        entity.playSound(SoundEvents.levelUp, 1f, 1f);
    }
}

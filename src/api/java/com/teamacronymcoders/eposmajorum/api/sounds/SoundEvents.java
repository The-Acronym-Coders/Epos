package com.teamacronymcoders.eposmajorum.api.sounds;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEvents {
    private static final ResourceLocation levelUpRL = new ResourceLocation(EposAPI.ID, "level-up");
    public static final SoundEvent levelUp = new SoundEvent(levelUpRL).setRegistryName(levelUpRL);
}

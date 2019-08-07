package com.teamacronymcoders.mcrpg.api.sounds;

import com.teamacronymcoders.mcrpg.api.MCRPGAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class MCRPGSoundEvents {
    private static final ResourceLocation levelUpRL = new ResourceLocation(MCRPGAPI.ID, "level-up");
    public static final SoundEvent levelUp = new SoundEvent(levelUpRL).setRegistryName(levelUpRL);
}

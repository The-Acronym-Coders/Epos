package com.teamacronymcoders.epos.sound;

import com.teamacronymcoders.epos.api.EposAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class EposSoundEvents {
    private static final ResourceLocation levelUpRL = new ResourceLocation(EposAPI.ID, "level-up");
    public static final SoundEvent levelUp = new SoundEvent(levelUpRL).setRegistryName(levelUpRL);
}

/*
 * Copyright (c) Team Acronym Coders
 * SPDX-License-Identifier: MIT
 */

package com.teamacronymcoders.epos;

import com.teamacronymcoders.epos.core.EposDatapackRegistries;
import com.teamacronymcoders.epos.data.EposData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The main mod class for this mod.
 */
@Mod(Epos.ID)
public class Epos {

    /**
     * The mod identifier for this mod.
     */
    public static final String ID = "epos";

    /**
     * Constructor to initialize basic information about this mod.
     */
    public Epos() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Setup registries
        EposDatapackRegistries.init(modBus);

        // Setup data
        EposData.init(modBus);
    }

    /**
     * Constructs a new {@link ResourceLocation} with this mod's id.
     *
     * @param path the path of the resource location
     * @return a new {@link ResourceLocation}
     */
    public static ResourceLocation withId(String path) {
        return new ResourceLocation(ID, path);
    }
}

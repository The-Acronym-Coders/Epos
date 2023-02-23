/*
 * Copyright (c) Team Acronym Coders
 * SPDX-License-Identifier: MIT
 */

package com.teamacronymcoders.epos.core;

import com.teamacronymcoders.epos.Epos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DataPackRegistryEvent;

//TODO: Document
public class EposDatapackRegistries {

    public static final ResourceKey<Registry<Skill>> SKILL_KEY = ResourceKey.createRegistryKey(Epos.withId("skill"));

    public static void init(IEventBus modBus) {
        modBus.addListener(EposDatapackRegistries::createDatapackRegistries);
    }

    private static void createDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(SKILL_KEY, Skill.CODEC);
    }
}

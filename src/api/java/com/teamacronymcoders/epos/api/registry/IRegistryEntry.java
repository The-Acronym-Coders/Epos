package com.teamacronymcoders.epos.api.registry;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IRegistryEntry {
    @Nonnull
    ResourceLocation getRegistryName();

    default boolean isFound() {
        return true;
    }
}

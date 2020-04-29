package com.teamacronymcoders.epos.api.registry;

import javax.annotation.Nonnull;
import net.minecraft.util.ResourceLocation;

public interface IRegistryEntry {
    @Nonnull
    ResourceLocation getRegistryName();

    default boolean isFound() {
        return true;
    }
}

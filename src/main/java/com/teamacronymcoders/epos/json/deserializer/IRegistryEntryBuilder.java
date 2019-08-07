package com.teamacronymcoders.mcrpg.json.deserializer;

import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.registry.IRegistryEntry;
import net.minecraft.util.ResourceLocation;

public interface IRegistryEntryBuilder<T extends IRegistryEntry> {
    T build(ResourceLocation registryName) throws JsonParseException;
}

package com.teamacronymcoders.epos.api.pathfeature;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class PathFeatureProvider extends ForgeRegistryEntry<PathFeatureProvider> {
    public abstract IPathFeature provide(JsonObject jsonObject) throws JsonParseException;
}

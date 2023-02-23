package com.teamacronymcoders.epos.api.path.features.provider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class PathFeatureProvider extends ForgeRegistryEntry<PathFeatureProvider> {

    public abstract IPathFeature provide(JsonObject object) throws JsonParseException;
}

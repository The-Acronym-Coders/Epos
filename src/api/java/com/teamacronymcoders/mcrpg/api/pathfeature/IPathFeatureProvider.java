package com.teamacronymcoders.mcrpg.api.pathfeature;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.mcrpg.api.registry.IRegistryEntry;

public interface IPathFeatureProvider extends IRegistryEntry {
    IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;
}

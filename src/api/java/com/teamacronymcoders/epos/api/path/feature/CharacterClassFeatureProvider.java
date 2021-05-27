package com.teamacronymcoders.epos.api.path.feature;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ClassFeatureProvider extends ForgeRegistryEntry<ClassFeatureProvider> {
    public abstract IClassFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;
}

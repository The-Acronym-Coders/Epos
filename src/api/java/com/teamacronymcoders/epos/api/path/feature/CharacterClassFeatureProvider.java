package com.teamacronymcoders.epos.api.path.feature;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class CharacterClassFeatureProvider extends ForgeRegistryEntry<CharacterClassFeatureProvider> {
    public abstract IClassFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;
}

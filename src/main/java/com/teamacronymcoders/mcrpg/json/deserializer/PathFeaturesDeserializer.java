package com.teamacronymcoders.eposmajorum.json.deserializer;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeature;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.eposmajorum.api.pathfeature.PathFeatures;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class PathFeaturesDeserializer implements JsonDeserializer<PathFeatures> {
    @Override
    public PathFeatures deserialize(JsonElement jsonElement, Type type,
                                    JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement  != null && jsonElement.isJsonArray()) {
            JsonObject featureObject = jsonElement.getAsJsonObject();
            Int2ObjectMap<List<IPathFeature>> pathFeaturesByLevel = new Int2ObjectOpenHashMap<>();
            for (Map.Entry<String,JsonElement> featureElement: featureObject.entrySet()) {
                int level;
                try {
                    level = Integer.parseInt(featureElement.getKey());
                    if (level < 1) {
                        throw new NumberFormatException();
                    }
                } catch(NumberFormatException exception) {
                    throw new JsonParseException("all features object keys must be numbers > 0");
                }
                if (featureElement.getValue().isJsonObject()) {
                    pathFeaturesByLevel.put(level, Lists.newArrayList(parseJsonObject(
                            featureElement.getValue().getAsJsonObject(), jsonDeserializationContext)));
                } else if (featureElement.getValue().isJsonArray()) {
                    List<IPathFeature> features = Lists.newArrayList();
                    for (JsonElement arrayElement: featureElement.getValue().getAsJsonArray()) {
                        if (arrayElement.isJsonObject()) {
                            features.add(parseJsonObject(arrayElement.getAsJsonObject(), jsonDeserializationContext));
                        } else {
                            throw new JsonParseException("all features object values must be objects or arrays of objects");
                        }
                    }
                    pathFeaturesByLevel.put(level, features);
                } else {
                    throw new JsonParseException("all features object values must be objects or arrays of objects");
                }
            }
            return new PathFeatures(pathFeaturesByLevel);
        }
        throw new JsonParseException("features was null or not an object");
    }

    private IPathFeature parseJsonObject(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException{
        JsonPrimitive providerPrimitive = jsonObject.getAsJsonPrimitive("provider");
        if (providerPrimitive != null && providerPrimitive.isString()) {
            IPathFeatureProvider provider = EposAPI.PATH_FEATURE_PROVIDER_REGISTRY
                    .getEntryOrMissing(providerPrimitive.getAsString());
            jsonObject.remove("provider");
            return provider.provide(jsonObject, context);

        }
        throw new JsonParseException("feature.provider must be a nonnull String");
    }
}

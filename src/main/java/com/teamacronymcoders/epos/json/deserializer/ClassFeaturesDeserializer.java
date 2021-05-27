package com.teamacronymcoders.epos.json.deserializer;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.path.feature.IClassFeature;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatureProvider;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatures;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ClassFeaturesDeserializer implements JsonDeserializer<CharacterClassFeatures> {
    @Override
    public CharacterClassFeatures deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement != null && jsonElement.isJsonArray()) {
            JsonObject featureObject = jsonElement.getAsJsonObject();
            Int2ObjectMap<List<IClassFeature>> pathFeaturesByLevel = new Int2ObjectOpenHashMap<>();
            for (Map.Entry<String, JsonElement> featureElement : featureObject.entrySet()) {
                int level;
                try {
                    level = Integer.parseInt(featureElement.getKey());
                    if (level < 1) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException exception) {
                    throw new JsonParseException("all features object keys must be numbers > 0");
                }
                if (featureElement.getValue().isJsonObject()) {
                    pathFeaturesByLevel.put(level, Lists.newArrayList(parseJsonObject(
                        featureElement.getValue().getAsJsonObject(), jsonDeserializationContext)));
                } else if (featureElement.getValue().isJsonArray()) {
                    List<IClassFeature> features = Lists.newArrayList();
                    for (JsonElement arrayElement : featureElement.getValue().getAsJsonArray()) {
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
            return new CharacterClassFeatures(pathFeaturesByLevel);
        }
        throw new JsonParseException("features was null or not an object");
    }

    private IClassFeature parseJsonObject(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        JsonPrimitive providerPrimitive = jsonObject.getAsJsonPrimitive("provider");
        if (providerPrimitive != null && providerPrimitive.isString()) {
            CharacterClassFeatureProvider provider = EposAPI.CLASS_FEATURE_PROVIDERS.getValue(new ResourceLocation(providerPrimitive.getAsString()));
            jsonObject.remove("provider");
            if (provider == null) {
                throw new JsonParseException("No Character Class Feature Provider found for name " + providerPrimitive.getAsString());
            }
            return provider.provide(jsonObject, context);
        }
        throw new JsonParseException("feature.provider must be a nonnull String");
    }
}

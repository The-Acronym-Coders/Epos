package com.teamacronymcoders.epos.json.jsonprovider;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeature;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatureProvider;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatures;
import com.teamacronymcoders.epos.path.Path;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public class PathJsonProvider implements IJsonProvider<IPath> {
    @Override
    public IPath provide(ResourceLocation key, JsonObject jsonObject) throws JsonParseException {
        return new Path(
                key,
                JsonUtils.getTranslation(jsonObject, "name", "path", key),
                JsonUtils.getTranslation(jsonObject, "description", "path", key),
                jsonObject.has("features") ? parsePathFeatures(jsonObject.getAsJsonObject("features")) : new PathFeatures()
        );
    }

    public PathFeatures parsePathFeatures(JsonObject jsonObject) {
        Int2ObjectMap<List<IPathFeature>> pathFeaturesByLevel = new Int2ObjectOpenHashMap<>();
        for (Map.Entry<String, JsonElement> featureElement : jsonObject.entrySet()) {
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
                        featureElement.getValue().getAsJsonObject())));
            } else if (featureElement.getValue().isJsonArray()) {
                List<IPathFeature> features = Lists.newArrayList();
                for (JsonElement arrayElement : featureElement.getValue().getAsJsonArray()) {
                    if (arrayElement.isJsonObject()) {
                        features.add(parseJsonObject(arrayElement.getAsJsonObject()));
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

    private IPathFeature parseJsonObject(JsonObject jsonObject) throws JsonParseException {
        String providerName = JsonUtils.getString(jsonObject, "provider");
        PathFeatureProvider provider = EposAPI.PATH_FEATURE_PROVIDER_REGISTRY
                .getValue(new ResourceLocation(providerName));
        if (provider != null) {
            return provider.provide(jsonObject);
        } else {
            throw new JsonParseException("no provider found for " + providerName);
        }
    }
}

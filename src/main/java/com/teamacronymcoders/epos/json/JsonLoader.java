package com.teamacronymcoders.epos.json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.json.jsondirector.IJsonDirector;
import com.teamacronymcoders.epos.json.jsonprovider.IJsonProvider;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class JsonLoader<T> extends JsonReloadListener {
    private final IJsonDirector<T> director;
    private final String type;
    private final Logger logger;
    private final IJsonProvider<T> jsonProvider;

    public JsonLoader(String type, Logger logger, IJsonDirector<T> director, IJsonProvider<T> jsonProvider) {
        super(new Gson(), type);
        this.type = type;
        this.logger = logger;
        this.director = director;
        this.jsonProvider = jsonProvider;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonObject> ts, IResourceManager resourceManager, IProfiler iProfiler) {
        director.clear();
        ts.entrySet()
                .parallelStream()
                .map(entry -> {
                    try {
                        return new Tuple<>(entry.getKey(), jsonProvider.provide(entry.getKey(), entry.getValue()));
                    } catch (JsonParseException exception) {
                        logger.error("Failed to load " + type + " for file " + entry.getKey().toString(), exception);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .forEach(tuple -> director.put(tuple.getA(), tuple.getB()));
        logger.info("Loaded " + ts.size() + " " + type);
    }
}
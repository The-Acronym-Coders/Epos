package com.teamacronymcoders.epos.api.pathfeature;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MissingPathFeatureProvider implements IPathFeatureProvider {
    private final ResourceLocation registryName;

    public MissingPathFeatureProvider(String registryName) {
        this.registryName = new ResourceLocation(registryName);
    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public boolean isFound() {
        return false;
    }

    @Override
    public IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        throw new JsonParseException("Missing Provider: " + registryName);
    }
}

package com.teamacronymcoders.mcrpg.pathfeature.feat;

import com.google.gson.*;
import com.teamacronymcoders.mcrpg.api.EposAPI;
import com.teamacronymcoders.mcrpg.api.feat.IFeat;
import com.teamacronymcoders.mcrpg.api.json.JsonUtils;
import com.teamacronymcoders.mcrpg.api.pathfeature.IPathFeature;
import com.teamacronymcoders.mcrpg.api.pathfeature.IPathFeatureProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class FeatFeatureProvider implements IPathFeatureProvider {
    private final ResourceLocation registryName = new ResourceLocation(EposAPI.ID, "feat");

    @Override
    public IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        String featName = JsonUtils.getString(jsonObject, "feat");
        IFeat feat = EposAPI.FEAT_REGISTRY.getEntry(featName);
        if (feat != null) {
            return new FeatFeature(feat);
        } else {
            throw new JsonParseException("No feat for registry name: " + featName);
        }

    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return this.registryName;
    }
}

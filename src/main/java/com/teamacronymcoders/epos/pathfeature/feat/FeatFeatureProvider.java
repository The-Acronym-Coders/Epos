package com.teamacronymcoders.epos.pathfeature.feat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeature;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatureProvider;
import net.minecraft.util.ResourceLocation;

public class FeatFeatureProvider extends PathFeatureProvider {
    @Override
    public IPathFeature provide(JsonObject jsonObject) {
        String featName = JsonUtils.getString(jsonObject, "feat");
        Feat feat = EposAPI.FEAT_REGISTRY.getValue(new ResourceLocation(featName));
        if (feat != null) {
            return new FeatFeature(feat);
        } else {
            throw new JsonParseException("No feat for registry name: " + featName);
        }

    }
}

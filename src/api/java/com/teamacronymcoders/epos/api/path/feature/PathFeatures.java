package com.teamacronymcoders.epos.api.path.feature;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.util.List;

public class PathFeatures {
    private final Int2ObjectMap<List<IPathFeature>> featuresByLevel;

    public PathFeatures(Int2ObjectMap<List<IPathFeature>> featuresByLevel) {
        this.featuresByLevel = featuresByLevel;
    }

    public List<IPathFeature> getFeaturesForLevel(int level) {
        return this.featuresByLevel.get(level);
    }
}

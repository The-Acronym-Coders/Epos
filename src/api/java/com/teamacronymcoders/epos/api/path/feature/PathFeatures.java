package com.teamacronymcoders.epos.api.path.feature;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

import java.util.List;

public class CharacterClassFeatures {
    private final Int2ObjectMap<List<IClassFeature>> featuresByLevel;

    public CharacterClassFeatures(Int2ObjectMap<List<IClassFeature>> featuresByLevel) {
        this.featuresByLevel = featuresByLevel;
    }

    public List<IClassFeature> getFeaturesForLevel(int level) {
        return this.featuresByLevel.get(level);
    }
}

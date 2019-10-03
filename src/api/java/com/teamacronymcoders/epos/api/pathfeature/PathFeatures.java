package com.teamacronymcoders.epos.api.pathfeature;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.network.PacketBuffer;

import java.util.List;

public class PathFeatures {
    private final Int2ObjectMap<List<IPathFeature>> featuresByLevel;

    public PathFeatures() {
        this(Int2ObjectMaps.emptyMap());
    }
    public PathFeatures(Int2ObjectMap<List<IPathFeature>> featuresByLevel) {
        this.featuresByLevel = featuresByLevel;
    }

    public List<IPathFeature> getFeaturesForLevel(int level) {
        return this.featuresByLevel.get(level);
    }
}

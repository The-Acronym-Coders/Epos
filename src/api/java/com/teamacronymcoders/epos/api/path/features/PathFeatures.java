package com.teamacronymcoders.epos.api.path.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;

import java.util.List;

public class PathFeatures {

//    public static final Codec<PathFeatures> CODEC = RecordCodecBuilder.create(instance -> instance
//        .group(
//            Codec.unboundedMap(Codec.INT, Codec.list(IPathFeature::getCodec))
//        )
//    );

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

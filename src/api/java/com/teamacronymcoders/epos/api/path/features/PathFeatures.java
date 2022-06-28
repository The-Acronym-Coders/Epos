package com.teamacronymcoders.epos.api.path.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.util.EposRegistries;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class PathFeatures {

    public static final Codec<PathFeatures> CODEC = RecordCodecBuilder.create(instance -> instance
                .group(
                        Codec.unboundedMap(Codec.INT, ResourceLocation.CODEC.comapFlatMap(id -> {
                            @Nullable IPathFeature feature = Epos.instance().getRegistries().getPathFeature(id);
                            return feature != null ? DataResult.success(feature) : DataResult.error("Path Feature does not exist: " + id);
                        }, feature -> EposRegistries.PATH_FEATURES.get().getKey(feature)).listOf())
                                .optionalFieldOf("pathFeatures", new Int2ObjectArrayMap<>())
                                .xmap(Int2ObjectArrayMap::new, map -> map).forGetter(PathFeatures::getPathFeatures)
                ).apply(instance, PathFeatures::new)
    );

    private final Int2ObjectArrayMap<List<IPathFeature>> featuresByLevel;

    public PathFeatures() {
        this(new Int2ObjectArrayMap<>());
    }

    public PathFeatures(Int2ObjectArrayMap<List<IPathFeature>> featuresByLevel) {
        this.featuresByLevel = featuresByLevel;
    }

    public List<IPathFeature> getFeaturesForLevel(int level) {
        return this.featuresByLevel.get(level);
    }

    private Int2ObjectArrayMap<List<IPathFeature>> getPathFeatures() {
        return this.featuresByLevel;
    }
}

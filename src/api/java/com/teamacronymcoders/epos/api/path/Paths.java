package com.teamacronymcoders.epos.api.path;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Paths {

    public static final Codec<Paths> CODEC = RecordCodecBuilder.create(instance -> instance
    .group(Codec.unboundedMap(ResourceLocation.CODEC, PathInfo.CODEC).fieldOf("paths").forGetter(Paths::getPathInfoMap))
    .apply(instance, Paths::new));

    private final Map<ResourceLocation, PathInfo> pathInfoMap;

    /**
     * Default Constructor
     */
    public Paths() {
        this.pathInfoMap = new HashMap<>();
    }

    /**
     * Codec Constructor
     * @param pathInfoMap Map of the currently stored Path(s) on the Character.
     */
    public Paths(Map<ResourceLocation, PathInfo> pathInfoMap) {
        this.pathInfoMap = pathInfoMap;
    }

    public PathInfo getOrCreate(ResourceLocation id) {
        return this.pathInfoMap.computeIfAbsent(id, resourceLocation -> new PathInfo());
    }

    public Map<ResourceLocation, PathInfo> getPathInfoMap() {
        return pathInfoMap;
    }
}

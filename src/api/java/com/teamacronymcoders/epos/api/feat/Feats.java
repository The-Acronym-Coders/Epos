package com.teamacronymcoders.epos.api.feat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Feats {

    public static final Codec<Feats> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(Codec.unboundedMap(ResourceLocation.CODEC, FeatInfo.CODEC).fieldOf("feats").forGetter(Feats::getFeatInfoMap))
        .apply(instance, Feats::new));

    private final Map<ResourceLocation, FeatInfo> featInfoMap;

    /**
     * Default Constructor
     */
    public Feats() {
        this.featInfoMap = new HashMap<>();
    }

    /**
     * Codec Constructor
     * @param featInfoMap Map of the currently stored Path(s) on the Character.
     */
    public Feats(Map<ResourceLocation, FeatInfo> featInfoMap) {
        this.featInfoMap = featInfoMap;
    }

    public FeatInfo getOrCreate(ResourceLocation id) {
        return this.featInfoMap.computeIfAbsent(id, resourceLocation -> new FeatInfo());
    }

    public Map<ResourceLocation, FeatInfo> getFeatInfoMap() {
        return featInfoMap;
    }
}

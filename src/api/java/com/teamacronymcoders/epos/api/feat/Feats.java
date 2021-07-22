package com.teamacronymcoders.epos.api.feat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Feats {

    public static final Codec<Feats> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Codec.unboundedMap(ResourceLocation.CODEC, FeatInfo.CODEC.dispatch(o -> o, FeatInfo::getCodec)).fieldOf("feats").forGetter(Feats::getFeatInfoMap))
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
     *
     * @param featInfoMap Map of the currently stored Path(s) on the Character.
     */
    public Feats(Map<ResourceLocation, FeatInfo> featInfoMap) {
        this.featInfoMap = featInfoMap;
    }

    public FeatInfo getOrCreate(ResourceLocation id) {
        FeatInfo registryInfo = Epos.instance().getRegistrate().getFeatInfoRegistry().get().getValue(id);
        if (registryInfo != null) {
            return this.featInfoMap.computeIfAbsent(id, resourceLocation -> registryInfo.create());
        }
        return this.featInfoMap.computeIfAbsent(id, resourceLocation -> new FeatInfo());
    }

    public Map<ResourceLocation, FeatInfo> getFeatInfoMap() {
        return featInfoMap;
    }
}

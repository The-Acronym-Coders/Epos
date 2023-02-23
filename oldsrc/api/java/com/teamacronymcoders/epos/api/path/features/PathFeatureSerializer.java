package com.teamacronymcoders.epos.api.path.features;

import com.mojang.serialization.Codec;
import net.ashwork.dynamicregistries.entry.CodecEntry;

public class PathFeatureSerializer extends CodecEntry<IPathFeature, PathFeatureSerializer> {

    private final Codec<? extends IPathFeature> codec;

    public PathFeatureSerializer(Codec<? extends IPathFeature> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends IPathFeature> entryCodec() {
        return this.codec;
    }

}

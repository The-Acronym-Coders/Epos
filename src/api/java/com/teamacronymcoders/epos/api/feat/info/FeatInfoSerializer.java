package com.teamacronymcoders.epos.api.feat.info;

import com.mojang.serialization.Codec;
import net.ashwork.dynamicregistries.entry.CodecEntry;

public class FeatInfoSerializer extends CodecEntry<FeatInfo, FeatInfoSerializer> {
    
    private final Codec<? extends FeatInfo> codec;

    public FeatInfoSerializer(Codec<? extends FeatInfo> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends FeatInfo> entryCodec() {
        return this.codec;
    }
}

package com.teamacronymcoders.epos.api.feat;

import com.mojang.serialization.Codec;
import net.ashwork.dynamicregistries.entry.CodecEntry;

public final class FeatSerializer extends CodecEntry<IFeat, FeatSerializer> {

    private final Codec<? extends IFeat> codec;

    public FeatSerializer(Codec<? extends IFeat> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends IFeat> entryCodec() {
        return this.codec;
    }
}

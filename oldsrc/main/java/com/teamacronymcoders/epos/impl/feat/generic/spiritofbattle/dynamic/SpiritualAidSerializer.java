package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import com.mojang.serialization.Codec;
import net.ashwork.dynamicregistries.entry.CodecEntry;

public class SpiritualAidSerializer extends CodecEntry<ISpiritualAid, SpiritualAidSerializer> {

    private final Codec<? extends ISpiritualAid> codec;

    public SpiritualAidSerializer(Codec<? extends ISpiritualAid> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends ISpiritualAid> entryCodec() {
        return this.codec;
    }

}

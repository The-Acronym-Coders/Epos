package com.teamacronymcoders.epos.api.path;

import com.mojang.serialization.Codec;
import net.ashwork.dynamicregistries.entry.CodecEntry;

public final class PathSerializer extends CodecEntry<IPath, PathSerializer> {

    private final Codec<? extends IPath> codec;

    public PathSerializer(Codec<? extends IPath> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends IPath> entryCodec() {
        return this.codec;
    }
}

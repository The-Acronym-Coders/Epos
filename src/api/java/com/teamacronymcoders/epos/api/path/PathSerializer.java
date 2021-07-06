package com.teamacronymcoders.epos.api.path;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public final class PathSerializer extends ForgeRegistryEntry<PathSerializer> implements ISerializer<IPath, PathSerializer> {

    private final Codec<? extends IPath> codec;

    public PathSerializer(Codec<? extends IPath> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends IPath> objectCodec() {
        return this.codec;
    }
}

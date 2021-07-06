package com.teamacronymcoders.epos.api.feat;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public final class FeatSerializer extends ForgeRegistryEntry<FeatSerializer> implements ISerializer<IFeat, FeatSerializer> {

    private final Codec<? extends IFeat> codec;

    public FeatSerializer(Codec<? extends IFeat> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends IFeat> objectCodec() {
        return this.codec;
    }
}

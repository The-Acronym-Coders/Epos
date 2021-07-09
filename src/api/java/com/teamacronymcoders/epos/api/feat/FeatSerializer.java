package com.teamacronymcoders.epos.api.feat;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import com.teamacronymcoders.epos.feat.Feat;
import com.teamacronymcoders.epos.registry.FeatRegistrar;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public final class FeatSerializer extends ForgeRegistryEntry<FeatSerializer> implements ICodecEntry<IFeat, FeatSerializer> {

    private final Codec<? extends IFeat> codec;

    public FeatSerializer(Codec<? extends IFeat> codec) {
        this.codec = codec;
    }

    @Override
    public <T> DataResult<Pair<IFeat, T>> decode(DynamicOps<T> ops, T input) {
        return codec.decode(ops, input);
    }

    @Override
    public <T> DataResult<T> encode(IFeat input, DynamicOps<T> ops, T prefix) {
        return FeatRegistrar.FEAT_SERIALIZER.get().encode(input, ops, prefix);
    }
}

package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.api.feat.info.FeatInfoSerializer;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class FeatInfoRegistrar {

    public static final RegistryEntry<FeatInfoSerializer> FEAT_INFO_SERIALIZER = Epos.instance().getRegistrate()
            .featInfoSerializer("feat", () -> {
                Codec<FeatInfo> codec = RecordCodecBuilder.create(instance -> instance
                        .group(Codec.BOOL.fieldOf("isUnlocked").forGetter(FeatInfo::isUnlocked))
                        .apply(instance, FeatInfo::new)
                );
                return codec;
            }).register();

    public static void register() {}
}

package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.feat.Feat;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class FeatRegistrar {

    public static final RegistryEntry<Codec<? extends IFeat>> FEAT_SERIALIZER = Epos.instance().getRegistrate()
            .featSerializer("default_feat_serializer", () -> {
                Codec<Feat> codec = RecordCodecBuilder.create(instance -> instance
                        .group(
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(Feat::getName),
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(Feat::getDescription),
                                Codec.BOOL.fieldOf("isAbility").forGetter(Feat::isAbility)
                        )
                        .apply(instance, Feat::new));
                return codec;
            }).register();

    public static void register() {}
}

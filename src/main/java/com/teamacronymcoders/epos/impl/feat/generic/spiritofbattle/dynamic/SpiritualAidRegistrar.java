package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class SpiritualAidRegistrar {

    public static final RegistryEntry<SpiritualAidSerializer> SPIRITUAL_AID_SERIALIZER = Epos.instance().getRegistrate()
            .spiritualAidSerializer("spiritualAid", () -> {
                Codec<ISpiritualAid> codec = RecordCodecBuilder.create(instance -> instance
                    .group(Codec.list(EposCodecs.EFFECT_INSTANCE).fieldOf("effects").forGetter(ISpiritualAid::getEffects)).apply(instance, SpiritualAid::new)
                );
                return codec;
            }).register();

    public static void register() {}
}

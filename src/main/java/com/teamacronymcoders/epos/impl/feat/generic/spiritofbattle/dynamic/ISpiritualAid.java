package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.ashwork.dynamicregistries.entry.IDynamicEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;
import java.util.function.Function;

public interface ISpiritualAid {

    /**
     * Codec for (de)serializing biome modifiers inline.
     * Mods can use this for data generation.
     */
    Codec<ISpiritualAid> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> EposRegistries.SPIRITUAL_AID_SERIALIZERS.get().getCodec())
            .dispatch(ISpiritualAid::codec, Function.identity());

    ResourceLocation getEntityId();

    List<MobEffectInstance> getEffects();

    Codec<? extends ISpiritualAid> codec();

}

package com.teamacronymcoders.epos.api.feat;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.ashwork.dynamicregistries.entry.IDynamicEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// TODO: Document Main Interface Object
public interface IFeat extends IDescribable {

    /**
     * Codec for (de)serializing biome modifiers inline.
     * Mods can use this for data generation.
     */
    Codec<IFeat> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> EposRegistries.FEAT_SERIALIZERS.get().getCodec())
            .dispatch(IFeat::codec, Function.identity());

    @NotNull String getTranslationKey(ResourceLocation id);

    /**
     * Returns a boolean value as to wheter the {@link IFeat} is an Tickable Ability or not!
     *
     * @return Returns whether the feat is an active ability or not.
     */
    boolean isAbility();

    Codec<? extends IFeat> codec();

}

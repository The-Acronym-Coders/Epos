package com.teamacronymcoders.epos.api.path.features;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.ashwork.dynamicregistries.entry.IDynamicEntry;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

// TODO: Document Main Interface Object
public interface IPathFeature extends IDescribable {

    /**
     * Codec for (de)serializing biome modifiers inline.
     * Mods can use this for data generation.
     */
    Codec<IPathFeature> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> EposRegistries.PATH_FEATURE_SERIALIZERS.get().getCodec())
            .dispatch(IPathFeature::codec, Function.identity());

    /**
     * Called to Apply the {@link IPathFeature} to the {@link LivingEntity} Character
     *
     * @param character The Character as {@link LivingEntity}.
     * @param stats The {@link ICharacterSheet} for the Character.
     */
    void applyTo(LivingEntity character, ICharacterSheet stats);

    /**
     * Called to Revoke the {@link IPathFeature} to the {@link LivingEntity} Character
     *
     * @param character The Character as {@link LivingEntity}.
     * @param stats The {@link ICharacterSheet} for the Character.
     */
    void removeFrom(LivingEntity character, ICharacterSheet stats);

    Codec<? extends IPathFeature> codec();
}

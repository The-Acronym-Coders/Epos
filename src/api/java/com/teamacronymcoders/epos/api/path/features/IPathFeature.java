package com.teamacronymcoders.epos.api.path.features;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import net.minecraft.entity.LivingEntity;

// TODO: Document Main Interface Object
public interface IPathFeature extends IDescribable {

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

    /**
     * @return Returns the {@link IPathFeature} Codec for the {@link IPathFeature}
     */
    Codec<? extends IPathFeature> getCodec();
}

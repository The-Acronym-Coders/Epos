package com.teamacronymcoders.epos.api.path.features;

import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.character.ICharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

// TODO: Document Main Interface Object
public interface IPathFeature extends IDescribable {

    /**
     * Called to Apply the PathFeature to the {@link LivingEntity} Character
     * @param character
     * @param stats
     */
    void applyTo(LivingEntity character, ICharacterStats stats);

    /**
     * Called to Revoke the PathFeature to the {@link LivingEntity} Character
     * @param character
     * @param stats
     */
    void removeFrom(LivingEntity character, ICharacterStats stats);
}

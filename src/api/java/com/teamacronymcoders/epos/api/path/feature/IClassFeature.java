package com.teamacronymcoders.epos.api.path.feature;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public interface IPathFeature {
    /**
     * @return The Name of the Entry.
     */
    ITextComponent getName();

    /**
     * @return The Description of the Entry.
     */
    ITextComponent getDescription();

    void applyTo(LivingEntity character, ICharacterStats characterStats);

    void removeFrom(LivingEntity character, ICharacterStats characterStats);
}

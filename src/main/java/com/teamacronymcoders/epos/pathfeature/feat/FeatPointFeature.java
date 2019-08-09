package com.teamacronymcoders.epos.pathfeature.feat;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.pathfeature.PathFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

// TODO: Wait for sky to implement Feat Points...
public class FeatPointFeature extends PathFeature {
    private final int featPoints;

    public FeatPointFeature(int featPoints) {
        super(new TranslationTextComponent("pathfeature.eposmajorum.featpoints.name", featPoints),
                new TranslationTextComponent("pathfeature.eposmajourm.featpoints.description", featPoints));
        this.featPoints = featPoints;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterStats characterStats) {
        // TODO: Implement Addition of Feat Points here
        // TODO: @SkySom Remember to allow the feat counter to be Negative!
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterStats characterStats) {
        // TODO: Implement Removal of Feat Points here.
    }
}

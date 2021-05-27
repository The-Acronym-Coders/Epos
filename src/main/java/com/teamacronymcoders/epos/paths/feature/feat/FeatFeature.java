package com.teamacronymcoders.epos.classes.feature.feat;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.EposCapabilities;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.feat.FeatSource;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.path.feature.IPathFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FeatFeature implements IPathFeature {
    public static final FeatSource FEATURE_SOURCE =
        new FeatSource(new ResourceLocation(EposAPI.ID, "feature"), false);

    private final IFeat feat;
    private final ITextComponent name;
    private final ITextComponent description;

    public FeatFeature(IFeat feat) {
        this.feat = feat;
        this.name = new TranslationTextComponent("pathfeature.epos.feat.name", feat.getName());
        this.description = new TranslationTextComponent("pathfeature.epos.feat.description", feat.getName());
    }

    @Override
    public ITextComponent getName() {
        return this.name;
    }

    @Override
    public ITextComponent getDescription() {
        return this.description;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterStats characterStats) {
        character.getCapability(EposCapabilities.CHARACTER_STATS)
            .ifPresent(iCharacterStats -> iCharacterStats
                .getFeats()
                .addFeat(feat, FEATURE_SOURCE));
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterStats characterStats) {
        character.getCapability(EposCapabilities.CHARACTER_STATS)
            .ifPresent(iCharacterStats -> iCharacterStats
                .getFeats()
                .removeFeat(feat));
    }
}

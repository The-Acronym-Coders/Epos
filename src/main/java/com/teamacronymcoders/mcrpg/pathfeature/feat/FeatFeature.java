package com.teamacronymcoders.eposmajorum.pathfeature.feat;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.EposCapabilities;
import com.teamacronymcoders.eposmajorum.api.characterstats.ICharacterStats;
import com.teamacronymcoders.eposmajorum.api.feat.FeatSource;
import com.teamacronymcoders.eposmajorum.api.feat.IFeat;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeature;
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
        this.name = new TranslationTextComponent("pathfeature.eposmajorum.feat.name", feat.getName());
        this.description = new TranslationTextComponent("pathfeature.eposmajorum.feat.description", feat.getName());
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

package com.teamacronymcoders.epos.api.feat;

import net.minecraft.util.text.TranslationTextComponent;

public interface IFeat {
    // FeatInfo
    FeatInfo createFeatInfo();

    // Visibility
    boolean isHidden();
    boolean isUnlocked();

    // Localization
    TranslationTextComponent getName();
    TranslationTextComponent getDescription();
}

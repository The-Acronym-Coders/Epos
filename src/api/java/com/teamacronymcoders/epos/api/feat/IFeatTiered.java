package com.teamacronymcoders.epos.api.feat;

import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashMap;
import java.util.Map;

public interface IFeatTiered extends IFeat {

    // Tiers
    default int getMinTier() {
        return 0;
    }

    default int getMaxTier() {
        return 4;
    }

    default Map<Integer, TranslationTextComponent> getTierNameMap() {
      return new HashMap<Integer, TranslationTextComponent>() {{
         put(0, new TranslationTextComponent("feat.tier.none"));
         put(1, new TranslationTextComponent("feat.tier.beginner"));
         put(2, new TranslationTextComponent("feat.tier.intermediary"));
         put(3, new TranslationTextComponent("feat.tier.advanced"));
         put(4, new TranslationTextComponent("feat.tier.master"));
      }};
    }

}

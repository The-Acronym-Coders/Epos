package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.path.feature.IPathFeature;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.Map;

public interface IPath {
    // PathInfo
    PathInfo createPathInfo();

    // Visibility
    boolean isHidden();
    boolean isUnlocked();

    // Levels
    default int getMinLevel() {
        return 0;
    }
    default int getMaxLevel() {
        return 10;
    }

    // Features
    List<IPathFeature> getAllPathFeatures();
    Map<Integer, List<IPathFeature>> getLevelToPathFeaturesMap();
    List<IPathFeature> getFeaturesForLevel(int level);

    // Localization
    TranslationTextComponent getName();
    TranslationTextComponent getDescription();
}

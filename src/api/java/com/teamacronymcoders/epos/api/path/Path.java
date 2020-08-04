package com.teamacronymcoders.epos.api.path;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.teamacronymcoders.epos.api.path.feature.IPathFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Path extends ForgeRegistryEntry<Path> implements IPath {

    private List<IPathFeature> featureList;
    private BiMap<Integer, List<IPathFeature>> levelToFeaturesMap;

    public Path() {
        featureList = new ArrayList<>();
        levelToFeaturesMap = HashBiMap.create();
    }

    @Override
    public PathInfo createPathInfo() {
        return new PathInfo(this);
    }

    @Override
    public boolean isUnlocked() {
        return true;
    }

    public void setFeatureList(List<IPathFeature> featureList) {
        this.featureList = featureList;
    }

    @Override
    public List<IPathFeature> getAllPathFeatures() {
        return featureList;
    }

    public void setLevelToFeaturesMap(BiMap<Integer, List<IPathFeature>> levelToFeaturesMap) {
        this.levelToFeaturesMap = levelToFeaturesMap;
    }

    @Override
    public Map<Integer, List<IPathFeature>> getLevelToPathFeaturesMap() {
        return levelToFeaturesMap;
    }

    @Override
    public List<IPathFeature> getFeaturesForLevel(int level) {
        return levelToFeaturesMap.get(level);
    }

    @Override
    public TranslationTextComponent getName() {
        final ResourceLocation id = this.getRegistryName();
        final String unlocalized = "path." + id.getNamespace() + "." + id.getPath();
        return new TranslationTextComponent(unlocalized);
    }

    @Override
    public TranslationTextComponent getDescription() {
        final ResourceLocation id = this.getRegistryName();
        final String unlocalized = "path." + id.getNamespace() + "." + id.getPath() + ".desc";
        return new TranslationTextComponent(unlocalized);
    }

    @Override
    public String toString() {
        return this.getRegistryName().toString();
    }

}

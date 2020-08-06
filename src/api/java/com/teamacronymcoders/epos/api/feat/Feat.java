package com.teamacronymcoders.epos.api.feat;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Feat extends ForgeRegistryEntry<Feat> implements IFeat {
    @Override
    public FeatInfo createFeatInfo() {
        return new FeatInfo(this);
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public boolean isUnlocked() {
        return true;
    }

    @Override
    public TranslationTextComponent getName() {
        final ResourceLocation id = this.getRegistryName();
        final String unlocalized = "feat." + id.getNamespace() + "." + id.getPath();
        return new TranslationTextComponent(unlocalized);
    }

    @Override
    public TranslationTextComponent getDescription() {
        final ResourceLocation id = this.getRegistryName();
        final String unlocalized = "feat." + id.getNamespace() + "." + id.getPath() + ".desc";
        return new TranslationTextComponent(unlocalized);
    }

    @Override
    public String toString() {
        return this.getRegistryName().toString();
    }
}

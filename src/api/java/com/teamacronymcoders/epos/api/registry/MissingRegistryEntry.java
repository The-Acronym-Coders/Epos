package com.teamacronymcoders.epos.api.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class MissingRegistryEntry<T extends INamedRegistryEntry<T>> implements INamedRegistryEntry<T> {
    private final ResourceLocation registryName;
    private final ITextComponent name;
    private final ITextComponent description;

    public MissingRegistryEntry(ResourceLocation registryName, String type) {
        this.registryName = registryName;
        this.name = new TranslationTextComponent(type + ".epos.missing.name");
        this.description = new TranslationTextComponent(type + "epos.missing.description");
    }

    @Override
    public ITextComponent getName() {
        return this.name;
    }

    @Override
    public ITextComponent getDescription() {
        return this.description;
    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return this.registryName;
    }

    @Override
    public boolean isFound() {
        return false;
    }
}

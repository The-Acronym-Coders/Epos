package com.teamacronymcoders.epos.api.feat;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.List;

public class Feat extends ForgeRegistryEntry<Feat> {
    private final List<FeatEventHandler<?>> eventHandlers;
    private final ITextComponent name;
    private final ITextComponent description;

    public Feat(ITextComponent name, ITextComponent description, List<FeatEventHandler<?>> eventHandlers) {
        this.name = name;
        this.description = description;
        this.eventHandlers = eventHandlers;
    }

    public List<FeatEventHandler<?>> getEventHandlers() {
        return eventHandlers;
    }

    public ITextComponent getName() {
        return this.name;
    }

    public ITextComponent getDescription() {
        return this.description;
    }
}

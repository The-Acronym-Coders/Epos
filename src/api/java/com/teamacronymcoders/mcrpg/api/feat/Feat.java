package com.teamacronymcoders.eposmajorum.api.feat;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.List;

public class Feat implements IFeat {
    private final List<FeatEventHandler<?>> eventHandlers;
    private final ResourceLocation registryName;
    private final ITextComponent name;
    private final ITextComponent description;

    public Feat(ResourceLocation registryName, ITextComponent name, ITextComponent description,
                List<FeatEventHandler<?>> eventHandlers) {
        this.name = name;
        this.description = description;
        this.eventHandlers = eventHandlers;
        this.registryName = registryName;
    }

    @Override
    public List<FeatEventHandler<?>> getEventHandlers() {
        return eventHandlers;
    }

    @Override
    @Nonnull
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public ITextComponent getName() {
        return this.name;
    }

    @Override
    public ITextComponent getDescription() {
        return this.description;
    }
}

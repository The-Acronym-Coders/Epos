package com.teamacronymcoders.epos.path.feature;

import com.teamacronymcoders.epos.api.path.feature.IPathFeature;
import net.minecraft.util.text.ITextComponent;

public abstract class PathFeature implements IPathFeature {
    private final ITextComponent name;
    private final ITextComponent description;

    public PathFeature(ITextComponent name, ITextComponent description) {
        this.name = name;
        this.description = description;
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

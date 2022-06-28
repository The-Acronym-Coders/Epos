package com.teamacronymcoders.epos.path.feature;

import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import net.ashwork.dynamicregistries.entry.DynamicEntry;
import net.minecraft.network.chat.MutableComponent;

public abstract class AbstractPathFeature implements IPathFeature {

    private final MutableComponent name;
    private final MutableComponent description;

    public AbstractPathFeature(MutableComponent name, MutableComponent description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public MutableComponent getName() {
        return this.name;
    }

    @Override
    public MutableComponent getDescription() {
        return this.description;
    }

}

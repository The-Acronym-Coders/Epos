package com.teamacronymcoders.epos.classes.feature;

import com.teamacronymcoders.epos.api.path.feature.IClassFeature;
import net.minecraft.util.text.ITextComponent;

public abstract class CharacterClassFeature implements IClassFeature {
    private final ITextComponent name;
    private final ITextComponent description;

    public CharacterClassFeature(ITextComponent name, ITextComponent description) {
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

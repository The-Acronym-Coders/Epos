package com.teamacronymcoders.epos.api.registry;

import net.minecraft.util.text.ITextComponent;

public interface INamedRegistryEntry<T extends INamedRegistryEntry<T>> extends IRegistryEntry, Comparable<T> {
    /**
     * @return The Name of the Entry.
     */
    ITextComponent getName();

    /**
     * @return The Description of the Entry.
     */
    ITextComponent getDescription();

    @Override
    default int compareTo(T o) {
        return this.getName().getString().compareTo(o.getName().getString());
    }
}

package com.teamacronymcoders.epos.json.jsondirector;

import com.teamacronymcoders.epos.api.registry.IRegistryEntry;
import com.teamacronymcoders.epos.api.registry.Registry;
import net.minecraft.util.ResourceLocation;

public class RegistryJsonDirector<T extends IRegistryEntry> implements IJsonDirector<T> {
    private final Registry<T> registry;

    public RegistryJsonDirector(Registry<T> registry) {
        this.registry = registry;
    }

    @Override
    public void put(ResourceLocation resourceLocation, T value) {
        registry.addEntry(value);
    }

    @Override
    public void clear() {
        registry.clear();
    }
}

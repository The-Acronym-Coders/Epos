package com.teamacronymcoders.eposmajorum.api.registry;

import net.minecraftforge.eventbus.api.GenericEvent;

import java.util.List;

public class RegistrationEvent<T extends IRegistryEntry> extends GenericEvent<T> {
    private final Registry<T> registry;

    public RegistrationEvent(Class<T> type, Registry<T> registry) {
        super(type);
        this.registry = registry;
    }

    public Registry<T> getRegistry() {
        return this.registry;
    }

    public void register(T entry) {
        this.getRegistry().addEntry(entry);
    }

    public void register(List<T> entries) {
        entries.forEach(this::register);
    }
}

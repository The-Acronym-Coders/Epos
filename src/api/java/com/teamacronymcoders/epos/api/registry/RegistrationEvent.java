package com.teamacronymcoders.epos.api.registry;

import java.util.List;
import net.minecraftforge.eventbus.api.GenericEvent;

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

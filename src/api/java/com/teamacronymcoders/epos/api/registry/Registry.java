package com.teamacronymcoders.epos.api.registry;

import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class Registry<T extends IRegistryEntry> {
    private final Map<String, T> entries;
    private final Function<String, T> getDefault;

    public Registry(Function<String, T> getDefault) {
        this.getDefault = getDefault;
        this.entries = Maps.newHashMap();
    }

    public void addEntry(T entry) {
        this.entries.put(entry.getRegistryName().toString(), entry);
    }

    @Nullable
    public T getEntry(String entryName) {
        return this.entries.get(entryName);
    }

    @Nonnull
    public T getEntryOrMissing(String entryName) {
        T entry = this.entries.get(entryName);
        if (entry == null) {
            entry = Objects.requireNonNull(this.getDefault.apply(entryName),
                    "Registry was unable to create missing value for entry:" + entryName);
        }
        return entry;
    }

    @Nullable
    public T getEntry(ResourceLocation entryName) {
        return this.getEntry(entryName.toString());
    }

    public Collection<T> getEntries() {
        return entries.values();
    }

    public void clear() {
        this.entries.clear();
    }
}

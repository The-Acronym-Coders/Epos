package com.teamacronymcoders.epos.api.source;

import com.teamacronymcoders.epos.api.EposAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Supplier;

public class SourceType extends ForgeRegistryEntry<SourceType> {
    private final Supplier<Source> sourceSupplier;

    public SourceType(Supplier<Source> sourceSupplier) {
        this.sourceSupplier = sourceSupplier;
    }

    public Source createSource() {
        return sourceSupplier.get();
    }

    public static Source deserializeSource(CompoundNBT nbt) {
        SourceType sourceType = EposAPI.SOURCE_TYPES.getValue(new ResourceLocation(nbt.getString("source_type")));
        if (sourceType != null) {
            Source source = sourceType.createSource();
            source.deserializeNBT(nbt.getCompound("source_nbt"));
            return source;
        }
        return null;
    }
}

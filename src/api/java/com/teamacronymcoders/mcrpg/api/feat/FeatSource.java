package com.teamacronymcoders.eposmajorum.api.feat;

import net.minecraft.util.ResourceLocation;

public class FeatSource {
    public final ResourceLocation id;
    public final boolean countsTowardsPoints;

    public FeatSource(ResourceLocation id, boolean countsTowardsPoints) {
        this.id = id;
        this.countsTowardsPoints = countsTowardsPoints;
    }
}

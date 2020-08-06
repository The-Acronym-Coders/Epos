package com.teamacronymcoders.epos.api.feat;

import net.minecraft.util.ResourceLocation;

public class MissingFeat extends Feat {
    private final ResourceLocation registryName;

    public MissingFeat(String registryName) {
        this(new ResourceLocation(registryName));
    }

    public MissingFeat(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}

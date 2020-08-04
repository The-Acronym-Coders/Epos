package com.teamacronymcoders.epos.api.path;

import net.minecraft.util.ResourceLocation;

public class MissingPath extends Path {
    private final ResourceLocation registryName;

    public MissingPath(String registryName) {
        this(new ResourceLocation(registryName));
    }

    public MissingPath(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

}

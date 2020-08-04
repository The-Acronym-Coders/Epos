package com.teamacronymcoders.epos.api.path;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class Paths implements INBTSerializable<CompoundNBT> {

    private Map<ResourceLocation, PathInfo> pathInfoMap;

    public Paths() {
        pathInfoMap = new HashMap<>();
    }

    // TODO: Figure Out how the fuck to serialize this all >.>
    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    // TODO: Figure Out how the fuck to deserialize this all >.>
    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }

    public static Paths createFromNBT(CompoundNBT nbt) {
        Paths paths = new Paths();
        paths.deserializeNBT(nbt);
        return paths;
    }
}

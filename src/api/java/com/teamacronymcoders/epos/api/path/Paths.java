package com.teamacronymcoders.epos.api.path;

import com.teamacronymcoders.epos.api.EposUtils;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class Paths implements INBTSerializable<ListNBT> {
    private final Map<ResourceLocation, PathInfo> pathInfoMap;

    public Paths() {
        pathInfoMap = new HashMap<>();
    }

    // TODO: Figure Out how the fuck to serialize this all >.>
    @Override
    public ListNBT serializeNBT() {
        return EposUtils.serializePaths(this, new ListNBT());
    }

    // TODO: Figure Out how the fuck to deserialize this all >.>
    @Override
    public void deserializeNBT(ListNBT nbt) {
        EposUtils.deserializePaths(this, nbt);
    }

    public Map<ResourceLocation, PathInfo> getPathInfoMap() {
        return pathInfoMap;
    }

    public static Paths createFromNBT(ListNBT nbt) {
        Paths paths = new Paths();
        paths.deserializeNBT(nbt);
        return paths;
    }
}

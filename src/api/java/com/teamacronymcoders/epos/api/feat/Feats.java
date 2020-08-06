package com.teamacronymcoders.epos.api.feat;

import com.teamacronymcoders.epos.api.EposUtils;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class Feats implements INBTSerializable<ListNBT> {
    private Map<ResourceLocation, FeatInfo> featInfoMap;

    public Feats() {
        featInfoMap = new HashMap<>();
    }

    // TODO: Figure Out how the fuck to serialize this all >.>
    @Override
    public ListNBT serializeNBT() {
        return EposUtils.serializeFeats(this, new ListNBT());
    }

    // TODO: Figure Out how the fuck to deserialize this all >.>
    @Override
    public void deserializeNBT(ListNBT nbt) {
        EposUtils.deserializeFeats(this, nbt);
    }

    public Map<ResourceLocation, FeatInfo> getFeatInfoMap() {
        return featInfoMap;
    }

    public static Feats createFromNBT(ListNBT nbt) {
        Feats feats = new Feats();
        feats.deserializeNBT(nbt);
        return feats;
    }
}

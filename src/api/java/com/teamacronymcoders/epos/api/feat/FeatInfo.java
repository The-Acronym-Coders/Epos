package com.teamacronymcoders.epos.api.feat;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class FeatInfo implements INBTSerializable<CompoundNBT>, Comparable<FeatInfo> {
    private final String registryName;
    private final IFeat feat;
    private boolean active;
    private CompoundNBT data;

    public FeatInfo(IFeat feat) {
        this.registryName = feat.toString();
        this.feat = feat;
        this.data = new CompoundNBT();
    }

    public FeatInfo(IFeatTiered tiered) {
        this.registryName = tiered.toString();
        this.feat = tiered;
        this.data = new CompoundNBT();
    }

    @Override
    public int compareTo(FeatInfo o) {
        return o.registryName.equals(this.registryName) && o.feat == this.feat && o.active == this.active && o.data.equals(this.data) ? 1 : 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("Active", active);
        nbt.put("Data", data);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.active = nbt.getBoolean("Active");
        this.data = nbt.getCompound("Data");
    }
}

package com.teamacronymcoders.epos.api.feat;

import net.minecraft.nbt.CompoundNBT;

public class TieredFeatInfo extends FeatInfo {
    private int tier;

    public TieredFeatInfo(IFeatTiered tiered) {
        super(tiered);
        this.tier = 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        nbt.putInt("Tier", tier);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        nbt.getInt("Tier");
    }
}

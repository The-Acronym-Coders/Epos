package com.teamacronymcoders.epos.api.feat;

import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class Feats implements INBTSerializable<CompoundNBT> {
    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }

    public static Feats createFromNBT(CompoundNBT nbt) {
        Feats feats = new Feats();
        feats.deserializeNBT(nbt);
        return feats;
    }
}

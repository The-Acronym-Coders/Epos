package com.teamacronymcoders.epos.api.ability;

import com.teamacronymcoders.epos.api.source.Source;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class AbilityInstance implements INBTSerializable<CompoundNBT> {
    private Source source;
    private Ability ability;

    @Override
    public CompoundNBT serializeNBT() {
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}

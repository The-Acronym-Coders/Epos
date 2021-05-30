package com.teamacronymcoders.epos.characterstats;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

public class CharacterStats implements ICharacterStats {

    public CharacterStats() {

    }

    public CharacterStats(CompoundNBT nbt) {
        this.deserializeNBT(nbt);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT characterNBT = new CompoundNBT();
        return characterNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}

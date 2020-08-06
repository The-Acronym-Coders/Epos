package com.teamacronymcoders.epos.api.source;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class Source implements INBTSerializable<CompoundNBT> {
    public abstract boolean isValid(ICharacterStats stats, LivingEntity character);
}

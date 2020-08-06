package com.teamacronymcoders.epos.api.capability;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.characterstats.CharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CharacterStatsProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    private final CharacterStats stats;
    private final LazyOptional<CharacterStats> optional;

    public void invalidate() {
        optional.invalidate();
    }

    public CharacterStatsProvider(LivingEntity character) {
        stats = new CharacterStats(character);
        optional = LazyOptional.of(() -> stats);
    }

    public CharacterStatsProvider(LivingEntity character, CompoundNBT nbt) {
        stats = new CharacterStats(character);
        stats.deserializeNBT(nbt);
        optional = LazyOptional.of(() -> stats);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EposAPI.CHARACTER_STATS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return stats.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        stats.deserializeNBT(nbt);
    }
}

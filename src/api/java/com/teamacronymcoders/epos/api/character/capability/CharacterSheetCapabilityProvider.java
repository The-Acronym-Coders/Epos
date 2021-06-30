package com.teamacronymcoders.epos.api.character.capability;

import com.teamacronymcoders.epos.api.capability.EposCapabilities;
import com.teamacronymcoders.epos.api.character.CharacterSheet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CharacterSheetCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    private final CharacterSheet sheet;
    private final LazyOptional<CharacterSheet> optional;

    public CharacterSheetCapabilityProvider(LivingEntity character) {
        this.sheet = new CharacterSheet(character);
        this.optional = LazyOptional.of(() -> sheet);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == EposCapabilities.CHARACTER_SHEET) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return this.sheet.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.sheet.deserializeNBT(nbt);
    }
}

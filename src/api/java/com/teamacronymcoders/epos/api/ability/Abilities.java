package com.teamacronymcoders.epos.api.ability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public class Abilities implements INBTSerializable<CompoundNBT> {
    private Map<ResourceLocation, AbilityInstance> abilities;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        abilities.forEach((name, instance) -> nbt.put(name.toString(), instance.serializeNBT()));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        for (String tagName: nbt.keySet()) {
            abilities.put(new ResourceLocation(tagName), null);
        }
    }
}

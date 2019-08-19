package com.teamacronymcoders.epos.capability;

import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class PosInvHandlerCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    private PosInvHandler handler;
    private final LazyOptional<IItemHandler> posInv = LazyOptional.of(() -> handler);

    public PosInvHandlerCapabilityProvider(@Nonnull PosInvHandler invHandler) {
        this.handler = invHandler;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return posInv.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("items", Objects.requireNonNull(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(handler, null)));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(handler, null, nbt.get("items"));
    }
}

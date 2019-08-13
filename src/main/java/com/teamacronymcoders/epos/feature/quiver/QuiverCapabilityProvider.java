package com.teamacronymcoders.epos.feature.quiver;

import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QuiverCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<IItemHandler> optional;

    QuiverCapabilityProvider(PosInvHandler handler) {
        this.optional = LazyOptional.of(() -> handler);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }
}

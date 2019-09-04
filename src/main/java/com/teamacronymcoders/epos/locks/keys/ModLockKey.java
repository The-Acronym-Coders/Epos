package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.api.locks.keys.NBTLockKey;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class ModLockKey extends NBTLockKey {

    @Nonnull
    private final String modName;

    public ModLockKey(@Nonnull String modName) {
        this(modName, null);
    }

    public ModLockKey(@Nonnull String modName, @Nullable CompoundNBT nbt) {
        super(nbt);
        this.modName = modName.toLowerCase();
    }

    @Nullable
    public static ModLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            return fromItem(stack.getItem(), stack.getTag());
        } else if (object instanceof Item) {
            return fromItem((Item) object, null);
        } else if (object instanceof BlockState) {
            return fromRegistryName(((BlockState) object).getBlock().getRegistryName(), null);
        } else if (object instanceof Block) {
            return fromRegistryName(((Block) object).getRegistryName(), null);
        } else if (object instanceof FluidStack) {
            FluidStack stack = (FluidStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            return fromFluid(stack.getFluid(), stack.getTag());
        } else if (object instanceof Fluid) {
            return fromFluid((Fluid) object, null);
        } else if (object instanceof FluidState) {
            return fromFluid(((FluidState) object).getFluid(), null);
        }
        //TODO: Support getting from things that implement IForgeRegistry??
        return null;
    }

    @Nullable
    private static ModLockKey fromItem(@Nonnull Item item, @Nullable CompoundNBT nbt) {
        return item == Items.AIR ? null : fromRegistryName(item.getRegistryName(), nbt);
    }

    @Nullable
    private static ModLockKey fromFluid(@Nonnull Fluid fluid, @Nullable CompoundNBT nbt) {
        return fluid == Fluids.EMPTY ? null : fromRegistryName(fluid.getRegistryName(), nbt);
    }

    @Nullable
    private static ModLockKey fromRegistryName(@Nullable ResourceLocation registryName, @Nullable CompoundNBT nbt) {
        //If the registry name is somehow null we can't instantiate a new mod lock
        // Should never happen but gets rid of the null pointer warning
        return registryName == null ? null : new ModLockKey(registryName.getNamespace(), nbt);
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isNotFuzzy() ? this : new ModLockKey(modName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ModLockKey) {
            ModLockKey other = (ModLockKey) o;
            return modName.equals(other.modName) && Objects.equals(nbt, other.nbt);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(modName, nbt);
    }
}
package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.api.locks.key.NBTLockKey;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModLockKey extends NBTLockKey {

    @Nonnull
    private final String modName;

    public ModLockKey(@Nonnull String modName) {
        this(modName, null);
    }

    public ModLockKey(@Nonnull String modName, @Nullable CompoundNBT nbt) {
        super(nbt);
        //TODO: Validate it is a valid modname?
        this.modName = modName.toLowerCase(Locale.ROOT);
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isFuzzy() ? new ModLockKey(modName) : this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof ModLockKey) {
            ModLockKey other = (ModLockKey) o;
            return modName.equals(other.modName) && Objects.equals(nbt, other.nbt);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(modName, nbt);
    }

    @Nullable
    public static ModLockKey fromObject(@Nonnull Object object) {
        if (object instanceof IForgeRegistryEntry<?>) {
            return fromRegistryEntry((IForgeRegistryEntry<?>) object, null);
        } else if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            return fromRegistryEntry(stack.getItem(), stack.getTag());
        } else if (object instanceof BlockState) {
            return fromRegistryEntry(((BlockState) object).getBlock(), null);
        } else if (object instanceof FluidStack) {
            FluidStack stack = (FluidStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            return fromRegistryEntry(stack.getFluid(), stack.getTag());
        } else if (object instanceof FluidState) {
            return fromRegistryEntry(((FluidState) object).getFluid(), null);
        }
        return null;
    }

    @Nullable
    private static <T> ModLockKey fromRegistryEntry(@Nonnull IForgeRegistryEntry<T> registryEntry, @Nullable CompoundNBT nbt) {
        //If the registry name is somehow null we can't instantiate a new mod lock
        // Should never happen but gets rid of the null pointer warning
        ResourceLocation registryName = registryEntry.getRegistryName();
        return registryName == null ? null : new ModLockKey(registryName.getNamespace(), nbt);
    }
}
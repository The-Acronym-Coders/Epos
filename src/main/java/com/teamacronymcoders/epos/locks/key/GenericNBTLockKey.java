package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.api.locks.key.NBTLockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;

/**
 * Used for locking items and fluids based on their NBT.
 */
public class GenericNBTLockKey extends NBTLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.GENERIC_NBT);

    /**
     * @apiNote Ensure that the given Compound is not empty.
     */
    public GenericNBTLockKey(@Nonnull CompoundNBT nbt) {
        super(nbt);
        //TODO: Force that it isn't empty or null as while the base NBTLockKey can handle it, there is no reason to allow it
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return NOT_FUZZY;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof GenericNBTLockKey && Objects.equals(nbt, ((GenericNBTLockKey) o).nbt);
    }

    @Override
    public int hashCode() {
        return nbt == null ? super.hashCode() : nbt.hashCode();
    }

    @Nullable
    public static GenericNBTLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            return !stack.isEmpty() && stack.hasTag() ? fromCompound(stack.getTag()) : null;
        } else if (object instanceof FluidStack) {
            FluidStack stack = (FluidStack) object;
            return !stack.isEmpty() && stack.hasTag() ? fromCompound(stack.getTag()) : null;
        } else if (object instanceof CompoundNBT) {
            return fromCompound((CompoundNBT) object);
        }
        return null;
    }

    @Nullable
    private static GenericNBTLockKey fromCompound(@Nonnull CompoundNBT nbt) {
        return nbt.isEmpty() ? null : new GenericNBTLockKey(nbt);
    }
}
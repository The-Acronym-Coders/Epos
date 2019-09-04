package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.api.locks.keys.NBTLockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class GenericNBTLockKey extends NBTLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.GENERIC_NBT);

    public GenericNBTLockKey(@Nullable CompoundNBT nbt) {
        super(nbt);
    }

    @Nullable
    public static GenericNBTLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            return stack.isEmpty() ? null : new GenericNBTLockKey(((ItemStack) object).getTag());
        } else if (object instanceof CompoundNBT) {
            return new GenericNBTLockKey((CompoundNBT) object);
        }
        return null;
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
}
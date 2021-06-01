package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.api.locks.key.NBTLockKey;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;

public class ItemLockKey extends NBTLockKey {

    @Nonnull
    private Item item;

    public ItemLockKey(@Nonnull Item item) {
        this(item, null);
    }

    public ItemLockKey(@Nonnull Item item, @Nullable CompoundNBT nbt) {
        super(nbt);
        this.item = item;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isNotFuzzy() ? this : new ItemLockKey(item);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemLockKey)) {
            return false;
        }
        ItemLockKey other = (ItemLockKey) o;
        return item == other.item && Objects.equals(nbt, other.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, nbt);
    }

    @Nullable
    public static ItemLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            return stack.isEmpty() ? null : new ItemLockKey(stack.getItem(), stack.getTag());
        } else if (object instanceof IItemProvider) {
            return new ItemLockKey(((IItemProvider) object).asItem());
        }
        return null;
    }
}
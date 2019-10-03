package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.api.locks.keys.NBTLockKey;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

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

    @Nullable
    public static ItemLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            return stack.isEmpty() ? null : new ItemLockKey(stack.getItem(), stack.getTag());
        } else if (object instanceof Item) {
            return new ItemLockKey((Item) object);
        }
        return null;
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
}
package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.api.locks.key.NBTLockKey;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

/**
 * Used for locking items based on their item type and optionally based on their NBT.
 */
public class ItemLockKey extends NBTLockKey {

    @Nonnull
    private Item item;

    private ItemLockKey(@Nonnull Item item) {
        this(item, null);
    }

    private ItemLockKey(@Nonnull Item item, @Nullable CompoundNBT nbt) {
        super(nbt);
        //TODO: Ensure nonnull and ensure non air
        this.item = item;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isFuzzy() ? new ItemLockKey(item) : this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof ItemLockKey) {
            ItemLockKey other = (ItemLockKey) o;
            return item == other.item && Objects.equals(nbt, other.nbt);
        }
        return false;
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
        } else if (object instanceof Item) {
            Item item = (Item) object;
            return item == Items.AIR ? null : new ItemLockKey(item);
        }
        return null;
    }
}
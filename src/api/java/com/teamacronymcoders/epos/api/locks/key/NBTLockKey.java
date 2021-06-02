package com.teamacronymcoders.epos.api.locks.key;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.ArrayUtils;

public abstract class NBTLockKey implements IFuzzyLockKey {

    @Nullable
    protected CompoundNBT nbt;

    protected NBTLockKey(@Nullable CompoundNBT nbt) {
        this.nbt = nbt == null || nbt.isEmpty() ? null : nbt;
    }

    protected static boolean similarNBT(@Nullable INBT full, @Nullable INBT partial) {
        if (full == null) {
            return partial == null;
        } else if (partial == null) {
            return true;
        } else if (full.getId() != partial.getId()) {
            return false;
        } else if (full.equals(partial)) {
            return true;
        }
        switch (full.getId()) {
            case Constants.NBT.TAG_COMPOUND:
                CompoundNBT fullTag = (CompoundNBT) full;
                CompoundNBT partialTag = (CompoundNBT) partial;
                Set<String> ptKeys = partialTag.keySet();
                for (String partialKey : ptKeys) {
                    //One of the keys is missing OR the tags are different types OR they do not match
                    if (!fullTag.contains(partialKey, partialTag.getTagId(partialKey)) || !similarNBT(fullTag.get(partialKey), partialTag.get(partialKey))) {
                        return false;
                    }
                }
                return true;
            case Constants.NBT.TAG_LIST:
                ListNBT fTagList = (ListNBT) full;
                ListNBT pTagList = (ListNBT) partial;
                if (fTagList.isEmpty() && !pTagList.isEmpty() || fTagList.getTagType() != pTagList.getTagType()) {
                    return false;
                }
                //TODO: Add similar support to this for making sure repeats get counted based on how many times they appear in partial
                // This is an edge case so isn't super important for now but is something to keep in mind
                for (INBT pTag : pTagList) {
                    boolean hasTag = false;
                    for (INBT inbt : fTagList) {
                        if (similarNBT(inbt, pTag)) {
                            hasTag = true;
                            break;
                        }
                    }
                    if (!hasTag) {
                        return false;
                    }
                }
                return true;
            case Constants.NBT.TAG_BYTE_ARRAY:
                return compareArrays(ArrayUtils.toObject(((ByteArrayNBT) full).getByteArray()), ArrayUtils.toObject(((ByteArrayNBT) partial).getByteArray()));
            case Constants.NBT.TAG_INT_ARRAY:
                return compareArrays(ArrayUtils.toObject(((IntArrayNBT) full).getIntArray()), ArrayUtils.toObject(((IntArrayNBT) partial).getIntArray()));
            case Constants.NBT.TAG_LONG_ARRAY:
                return compareArrays(ArrayUtils.toObject(((LongArrayNBT) full).getAsLongArray()), ArrayUtils.toObject(((LongArrayNBT) partial).getAsLongArray()));
            default:
                return false;
        }
    }

    private static <T extends Comparable<T>> boolean compareArrays(T[] fullArray, T[] partialArray) {
        List<Integer> hits = new ArrayList<>();
        for (T partial : partialArray) {
            boolean hasMatch = false;
            for (int i = 0; i < fullArray.length; i++) {
                if (!hits.contains(i) && partial.compareTo(fullArray[i]) == 0) {
                    hits.add(i);
                    hasMatch = true;
                    break;
                }
            }
            if (!hasMatch) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public CompoundNBT getNBT() {
        return this.nbt;
    }

    @Override
    public boolean isFuzzy() {
        return this.nbt != null;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey other) {
        if (other == this) {
            return true;
        } else if (other instanceof NBTLockKey) {
            return similarNBT(getNBT(), ((NBTLockKey) other).getNBT());
        }
        return false;
    }
}
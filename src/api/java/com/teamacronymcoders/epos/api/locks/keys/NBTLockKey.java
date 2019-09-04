package com.teamacronymcoders.epos.api.locks.keys;

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

public abstract class NBTLockKey implements IFuzzyLockKey {

    @Nullable
    protected CompoundNBT nbt;

    protected NBTLockKey(@Nullable CompoundNBT nbt) {
        this.nbt = nbt == null || nbt.isEmpty() ? null : nbt;
    }

    //TODO: Re-evaluate this and try to deduplicate the code and make sure tag list isn't checking duplicates
    protected static boolean similarNBT(@Nullable INBT full, @Nullable INBT partial) {
        if (full == null) {
            return partial == null;
        }
        if (partial == null) {
            return true;
        }
        if (full.getId() != partial.getId()) {
            return false;
        }
        if (full.equals(partial)) {
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
                for (int i = 0; i < pTagList.size(); i++) {
                    INBT pTag = pTagList.get(i);
                    boolean hasTag = false;
                    for (int j = 0; j < fTagList.size(); j++) {
                        if (similarNBT(fTagList.get(j), pTag)) {
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
                byte[] fByteArray = ((ByteArrayNBT) full).getByteArray();
                byte[] pByteArray = ((ByteArrayNBT) partial).getByteArray();
                List<Integer> hits = new ArrayList<>();
                for (byte pByte : pByteArray) {
                    boolean hasMatch = false;
                    for (int i = 0; i < fByteArray.length; i++) {
                        if (!hits.contains(i) && pByte == fByteArray[i]) {
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
            case Constants.NBT.TAG_INT_ARRAY:
                int[] fIntArray = ((IntArrayNBT) full).getIntArray();
                int[] pIntArray = ((IntArrayNBT) partial).getIntArray();
                hits = new ArrayList<>();
                for (int pInt : pIntArray) {
                    boolean hasMatch = false;
                    for (int i = 0; i < fIntArray.length; i++) {
                        if (!hits.contains(i) && pInt == fIntArray[i]) {
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
            case Constants.NBT.TAG_LONG_ARRAY:
                long[] fLongArray = ((LongArrayNBT) full).getAsLongArray();
                long[] pLongArray = ((LongArrayNBT) partial).getAsLongArray();
                hits = new ArrayList<>();
                for (long pLong : pLongArray) {
                    boolean hasMatch = false;
                    for (int i = 0; i < fLongArray.length; i++) {
                        if (!hits.contains(i) && pLong == fLongArray[i]) {
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
            default:
                return false;
        }
    }

    @Nullable
    public CompoundNBT getNBT() {
        return this.nbt;
    }

    @Override
    public boolean isNotFuzzy() {
        return this.nbt == null;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey other) {
        if (other == this) {
            return true;
        }
        if (other instanceof NBTLockKey) {
            return similarNBT(getNBT(), ((NBTLockKey) other).getNBT());
        }
        return false;
    }
}
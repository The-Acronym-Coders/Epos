package com.teamacronymcoders.epos.locks.keys.tag;

import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.api.locks.keys.NBTLockKey;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

//TODO: Do we want a param that says "what type" the tag is for (would it even make sense to have one like that)
public class TagLockKey extends NBTLockKey {

    private final ResourceLocation tag;

    public TagLockKey(ResourceLocation tag) {
        this(tag, null);
    }

    public TagLockKey(@Nonnull ResourceLocation tag, @Nullable CompoundNBT nbt) {
        super(nbt);
        this.tag = tag;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isNotFuzzy() ? this : new TagLockKey(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof TagLockKey && tag.equals(((TagLockKey) o).tag)) {
            return getNBT() == null ? ((TagLockKey) o).getNBT() == null : getNBT().equals(((TagLockKey) o).getNBT());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, nbt);
    }
}
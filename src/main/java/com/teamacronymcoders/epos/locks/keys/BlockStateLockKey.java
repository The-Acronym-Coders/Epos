package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

//TODO: Figure out the fuzzy and non fuzzy state of this. Use BlockStateMatcher to match blockstates
public class BlockStateLockKey implements IFuzzyLockKey {

    @Nonnull
    private BlockState state;

    public BlockStateLockKey(@Nonnull BlockState state) {
        this.state = state;
    }

    @Nullable
    public static BlockStateLockKey fromObject(@Nonnull Object object) {
        if (object instanceof BlockState) {
            return new BlockStateLockKey((BlockState) object);
        } else if (object instanceof Block) {
            return new BlockStateLockKey(((Block) object).getDefaultState());
        }
        return null;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        if (o == this) {
            return true;
        }
        if (o instanceof BlockStateLockKey) {
            BlockStateLockKey other = (BlockStateLockKey) o;
            //TODO
        }
        return false;
    }

    @Override
    public boolean isNotFuzzy() {
        //TODO
        return false;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isNotFuzzy() ? this : new BlockStateLockKey(state);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BlockStateLockKey)) {
            return false;
        }
        BlockStateLockKey other = (BlockStateLockKey) o;
        //TODO
        return false;//item == other.item && Objects.equals(nbt, other.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
package com.teamacronymcoders.epos.locks.key.harvest;

import com.teamacronymcoders.epos.api.locks.key.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

/**
 * Used for locking blocks based on the harvest level of the block.
 */
public class BlockHarvestLockKey extends HarvestLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.BLOCK_HARVEST);

    public BlockHarvestLockKey(int harvestLevel) {
        super(harvestLevel);
        //TODO: Block lock key based on "required tool type"???
        // Maybe should be part of this lock key (with it being nullable)
        // also would it be using getHarvestTool or isToolEffective
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof BlockHarvestLockKey && harvestLevel >= ((BlockHarvestLockKey) o).harvestLevel;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return NOT_FUZZY;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof BlockHarvestLockKey && harvestLevel == ((BlockHarvestLockKey) o).harvestLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(harvestLevel);
    }

    @Nullable
    public static BlockHarvestLockKey fromObject(@Nonnull Object object) {
        if (object instanceof BlockState) {
            return fromState((BlockState) object);
        } else if (object instanceof Block) {
            return fromState(((Block) object).getDefaultState());
        }
        return null;
    }

    @Nullable
    private static BlockHarvestLockKey fromState(@Nonnull BlockState state) {
        //TODO - 1.17: Remove comment
        //Note: This air check isn't "perfect" due to not being positional based, but given the positional based air is going away in 1.17 it should be fine for now
        if (state.isAir()) {
            return null;
        }
        int harvestLevel = state.getHarvestLevel();
        return harvestLevel < 0 ? null : new BlockHarvestLockKey(harvestLevel);
    }
}
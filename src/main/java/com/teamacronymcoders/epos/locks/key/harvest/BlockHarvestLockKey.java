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
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;

public class BlockHarvestLockKey extends HarvestLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.BLOCK_HARVEST);

    /**
     * @apiNote Ensure that the given harvest level is positive.
     */
    public BlockHarvestLockKey(int harvestLevel) {
        super(harvestLevel);
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
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            Block block = Block.getBlockFromItem(stack.getItem());
            return block == Blocks.AIR ? null : new BlockHarvestLockKey(block.getHarvestLevel(block.getDefaultState()));
        } else if (object instanceof BlockState) {
            return new BlockHarvestLockKey(((BlockState) object).getHarvestLevel());
        } else if (object instanceof Block) {
            Block block = (Block) object;
            return new BlockHarvestLockKey(block.getHarvestLevel(block.getDefaultState()));
        }
        return null;
    }
}
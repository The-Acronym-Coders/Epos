package com.teamacronymcoders.epos.locks.key.harvest;

import com.teamacronymcoders.epos.api.locks.key.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

public class ToolHarvestLockKey extends HarvestLockKey {

    private static final List<ToolHarvestLockKey> EMPTY = Collections.emptyList();
    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.TOOL_HARVEST);

    @Nonnull
    private final ToolType toolType;

    /**
     * @apiNote Ensure that the given harvest level is positive.
     */
    public ToolHarvestLockKey(@Nonnull ToolType toolType, int harvestLevel) {
        super(harvestLevel);
        this.toolType = toolType;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ToolHarvestLockKey) {
            ToolHarvestLockKey toolLock = (ToolHarvestLockKey) o;
            return harvestLevel >= toolLock.harvestLevel && toolType.equals(toolLock.toolType);
        }
        return false;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return NOT_FUZZY;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ToolHarvestLockKey) {
            ToolHarvestLockKey toolLock = (ToolHarvestLockKey) o;
            return harvestLevel == toolLock.harvestLevel && toolType.equals(toolLock.toolType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toolType, harvestLevel);
    }

    @Nonnull
    public static List<ToolHarvestLockKey> getKeysFromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return EMPTY;
            }
            List<ToolHarvestLockKey> keys = new ArrayList<>();
            Item item = stack.getItem();
            for (ToolType type : item.getToolTypes(stack)) {
                int level = item.getHarvestLevel(stack, type, null, null);
                if (level >= 0) {
                    keys.add(new ToolHarvestLockKey(type, level));
                }
            }
            return keys;
        }
        return EMPTY;
    }
}
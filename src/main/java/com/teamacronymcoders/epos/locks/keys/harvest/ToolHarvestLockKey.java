package com.teamacronymcoders.epos.locks.keys.harvest;

import com.teamacronymcoders.epos.api.locks.keys.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

//TODO: Add back in some form of support based on tool type
// Or should that be part of the logic handler abilities that merges this with another for handling specific tool types
public class ToolHarvestLockKey extends HarvestLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.TOOL_HARVEST);

    @Nonnull
    private final Map<ToolType, Integer> typeLevels;
    @Nullable
    private final ToolType toolType;

    public ToolHarvestLockKey(@Nullable ToolType toolType, int harvestLevel) {
        super(harvestLevel);
        this.toolType = toolType;
        this.typeLevels = new HashMap<>();
    }

    private ToolHarvestLockKey(@Nonnull Map<ToolType, Integer> typeLevels, int harvestLevel) {
        super(harvestLevel);
        this.toolType = null;
        this.typeLevels = typeLevels;
    }

    @Nullable
    public static ToolHarvestLockKey fromItemStack(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        int highestLevel = -1;
        Item item = stack.getItem();
        Map<ToolType, Integer> typeLevels = new HashMap<>();
        for (ToolType type : item.getToolTypes(stack)) {
            int level = item.getHarvestLevel(stack, type, null, null);
            if (level < 0) {
                continue;
            }
            typeLevels.put(type, level);
            if (level > highestLevel) {
                highestLevel = level;
            }
        }
        //TODO: Decide if it matches only one tool type if it should just be that type instead of it as a map
        // Note: Would have to rewrite fuzzyEquals implementation
        return highestLevel == -1 ? null : new ToolHarvestLockKey(typeLevels, highestLevel);
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ToolHarvestLockKey) {
            ToolHarvestLockKey toolLock = (ToolHarvestLockKey) o;
            if (harvestLevel >= toolLock.harvestLevel) {
                if (toolLock.toolType == null) {
                    return toolType == null;
                }
                return toolLock.typeLevels.keySet().stream().noneMatch(s -> !typeLevels.containsKey(s) || typeLevels.get(s) < toolLock.typeLevels.get(s));
            }
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
            if (toolType == null) {
                return toolLock.toolType == null && harvestLevel == toolLock.harvestLevel;
            }
            return harvestLevel == toolLock.harvestLevel && toolType.equals(toolLock.toolType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toolType, harvestLevel);
    }
}
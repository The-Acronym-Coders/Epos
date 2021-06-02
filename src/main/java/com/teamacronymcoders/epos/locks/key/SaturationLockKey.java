package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Used for locking food items based on their provided saturation value.
 */
public class SaturationLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.SATURATION);

    private final float saturation;

    public SaturationLockKey(float saturation) {
        if (saturation < 0) {
            throw new IllegalArgumentException("Saturation value must be at least zero.");
        }
        this.saturation = saturation;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof SaturationLockKey && saturation >= ((SaturationLockKey) o).saturation;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return NOT_FUZZY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(saturation);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof SaturationLockKey && saturation == ((SaturationLockKey) o).saturation;
    }

    @Nullable
    public static SaturationLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            return fromItem(stack.getItem());
        } else if (object instanceof Item) {
            return fromItem((Item) object);
        } else if (object instanceof Food) {
            return fromFood((Food) object);
        }
        return null;
    }

    @Nullable
    private static SaturationLockKey fromItem(@Nonnull Item item) {
        return item.isFood() ? fromFood(item.getFood()) : null;
    }

    @Nullable
    private static SaturationLockKey fromFood(@Nullable Food food) {
        if (food == null) {
            return null;
        }
        //Calculate actual saturation value based on the healing the food provides and the saturation modifier of the food
        float saturation = food.getHealing() * food.getSaturation() * 2F;
        //Ensure that the value is actually positive
        return saturation < 0 ? null : new SaturationLockKey(saturation);
    }
}
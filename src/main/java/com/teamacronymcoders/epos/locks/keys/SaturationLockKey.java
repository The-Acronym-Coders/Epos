package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SaturationLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.SATURATION);

    private final float saturation;

    public SaturationLockKey(float saturation) {
        if (saturation < 0) {
            //TODO: Add whatever information is needed here for a better error message
            throw new IllegalArgumentException();
        }
        this.saturation = saturation;
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
        //TODO: Check if the saturation is accurate or if it is what used to be saturation modifier
        // If it is old equiv then it should be
        // saturation = food.getHealing() * food.getSaturation() * 2f;
        return food == null ? null : new SaturationLockKey(food.getSaturation());
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof SaturationLockKey && saturation >= ((SaturationLockKey) o).saturation;
    }

    @Override
    public boolean isNotFuzzy() {
        return false;
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
}
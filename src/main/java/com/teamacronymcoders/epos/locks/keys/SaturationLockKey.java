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

    /**
     * @apiNote Ensure that the given saturation value is positive.
     */
    public SaturationLockKey(float saturation) {
        this.saturation = saturation;
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
        float saturation = food.getHealing() * food.getSaturation() * 2F;
        //Ensure that the value is actually positive
        return saturation < 0 ? null : new SaturationLockKey(saturation);
    }
}
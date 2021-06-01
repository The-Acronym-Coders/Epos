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
import net.minecraft.util.IItemProvider;
import org.lwjgl.system.CallbackI.I;

public class HungerLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.HUNGER);

    private final int hunger;

    /**
     * @apiNote Ensure that the given hunger value is positive.
     */
    public HungerLockKey(int hunger) {
        this.hunger = hunger;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof HungerLockKey && hunger >= ((HungerLockKey) o).hunger;
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
        return Objects.hash(hunger);
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof HungerLockKey && hunger == ((HungerLockKey) o).hunger;
    }

    @Nullable
    public static HungerLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            return fromItem(stack.getItem());
        } else if (object instanceof IItemProvider) {
            return fromItem(((IItemProvider) object).asItem());
        } else if (object instanceof Food) {
            return fromFood((Food) object);
        }
        return null;
    }

    @Nullable
    private static HungerLockKey fromItem(@Nonnull Item item) {
        return item.isFood() ? fromFood(item.getFood()) : null;
    }

    @Nullable
    private static HungerLockKey fromFood(@Nullable Food food) {
        if (food == null) {
            return null;
        }
        int healing = food.getHealing();
        //Ensure that the value is actually positive
        return healing < 0 ? null : new HungerLockKey(healing);
    }
}
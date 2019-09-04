package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import java.util.Collection;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArmorToughnessLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.ARMOR_TOUGHNESS);

    private final double toughness;

    public ArmorToughnessLockKey(double toughness) {
        if (toughness < 0) {
            throw new IllegalArgumentException("Armor toughness must be greater than or equal to zero. Received: '" + toughness + "'.");
        }
        this.toughness = toughness;
    }

    @Nullable
    public static ArmorToughnessLockKey fromItemStack(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Item item = stack.getItem();
        if (item instanceof ArmorItem) {
            ArmorItem armorItem = (ArmorItem) item;
            double toughness = armorItem.getToughness();
            Collection<AttributeModifier> protection = armorItem.getAttributeModifiers(armorItem.getEquipmentSlot(), stack).get(SharedMonsterAttributes.ARMOR_TOUGHNESS.getName());
            if (!protection.isEmpty()) {
                toughness += protection.stream().findFirst().map(AttributeModifier::getAmount).orElse(0D);
            }
            return new ArmorToughnessLockKey(toughness);
        }
        return null;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof ArmorToughnessLockKey && toughness >= ((ArmorToughnessLockKey) o).toughness;
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
    public boolean equals(Object o) {
        return o == this || o instanceof ArmorToughnessLockKey && toughness == ((ArmorToughnessLockKey) o).toughness;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toughness);
    }
}
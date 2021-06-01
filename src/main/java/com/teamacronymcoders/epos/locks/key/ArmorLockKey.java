package com.teamacronymcoders.epos.locks.key;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.epos.api.locks.key.GenericLockKey;
import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.locks.FuzzyLockKeyTypes;
import com.teamacronymcoders.epos.util.AttributeUtils;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArmorLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.ARMOR);

    private final double armor;

    /**
     * @apiNote Ensure that the given armor value is positive.
     */
    public ArmorLockKey(double armor) {
        this.armor = armor;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof ArmorLockKey && armor >= ((ArmorLockKey) o).armor;
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
        return o == this || o instanceof ArmorLockKey && armor == ((ArmorLockKey) o).armor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(armor);
    }

    @Nullable
    public static ArmorLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            Item item = stack.getItem();
            if (item instanceof ArmorItem) {
                ArmorItem armorItem = (ArmorItem) item;
                Multimap<Attribute, AttributeModifier> attributeModifiers = armorItem.getAttributeModifiers(armorItem.getEquipmentSlot(), stack);
                double armor = AttributeUtils.calculateValueFromModifiers(attributeModifiers, Attributes.ARMOR, armorItem.getDamageReduceAmount());
                //Ensure that the value is actually positive
                if (armor >= 0) {
                    return new ArmorLockKey(armor);
                }
            }
        }
        return null;
    }
}
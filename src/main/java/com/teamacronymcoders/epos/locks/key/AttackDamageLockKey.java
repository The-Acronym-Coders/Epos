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
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

/**
 * Used for locking items based on their attack damage value.
 */
public class AttackDamageLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.ATTACK_DAMAGE);

    private final double attackDamage;

    public AttackDamageLockKey(double attackDamage) {
        if (attackDamage < 0) {
            throw new IllegalArgumentException("Attack damage value must be at least zero.");
        }
        this.attackDamage = attackDamage;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof AttackDamageLockKey && attackDamage >= ((AttackDamageLockKey) o).attackDamage;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return NOT_FUZZY;
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof AttackDamageLockKey && attackDamage == ((AttackDamageLockKey) o).attackDamage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackDamage);
    }

    @Nullable
    public static AttackDamageLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            Multimap<Attribute, AttributeModifier> attributeModifiers = stack.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, stack);
            double damage = AttributeUtils.calculateValueFromModifiers(attributeModifiers, Attributes.ATTACK_DAMAGE);
            //Ensure that the value is actually positive
            if (damage >= 0) {
                return new AttackDamageLockKey(damage);
            }
        }
        return null;
    }
}
package com.teamacronymcoders.epos.locks.keys;

import com.google.common.collect.Multimap;
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
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AttackDamageLockKey implements IFuzzyLockKey {

    private static final GenericLockKey NOT_FUZZY = new GenericLockKey(FuzzyLockKeyTypes.ATTACK_DAMAGE);

    private final double attackDamage;

    public AttackDamageLockKey(double attackDamage) {
        if (attackDamage < 0) {
            throw new IllegalArgumentException("Damage value must be greater than or equal to zero. Received: '" + attackDamage + "'.");
        }
        this.attackDamage = attackDamage;
    }

    @Nullable
    public static AttackDamageLockKey fromItemStack(@Nonnull ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        Item item = stack.getItem();
        Multimap<String, AttributeModifier> attributeModifiers = item.getAttributeModifiers(EquipmentSlotType.MAINHAND, stack);
        Collection<AttributeModifier> damage = attributeModifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
        return damage.isEmpty() ? null : new AttackDamageLockKey(damage.stream().findFirst().map(AttributeModifier::getAmount).orElse(0D));
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        return o == this || o instanceof AttackDamageLockKey && attackDamage >= ((AttackDamageLockKey) o).attackDamage;
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
        return o == this || o instanceof AttackDamageLockKey && attackDamage == ((AttackDamageLockKey) o).attackDamage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attackDamage);
    }
}
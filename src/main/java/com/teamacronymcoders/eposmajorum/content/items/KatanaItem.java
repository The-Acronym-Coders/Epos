package com.teamacronymcoders.eposmajorum.content.items;

import com.google.common.collect.Multimap;
import com.teamacronymcoders.eposmajorum.api.EposCapabilities;
import com.teamacronymcoders.eposmajorum.api.characterstats.ICharacterStats;
import com.teamacronymcoders.eposmajorum.api.skill.SkillInfo;
import com.teamacronymcoders.eposmajorum.utils.UtilMethods;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

//TODO: Change from onUsingTick to dedicated onSkillLevelUp Event once that's available
public class KatanaItem extends SwordItem {
    private final double attackDamage;
    private final double attackSpeed;

    public KatanaItem() {
        super(EMItemTiers.KATANA, 3, 2, new Item.Properties().setNoRepair().rarity(Rarity.EPIC).maxStackSize(1));
        this.attackDamage = 3d;
        this.attackSpeed = 2d;
    }

    private double getAttackDamage(ItemStack stack, ICharacterStats stats) {
        if (stack.getEquipmentSlot() != EquipmentSlotType.MAINHAND) {
            return 0d;
        }
        SkillInfo wayOfTheSword = stats.getSkills().get("eposmajorum:way_of_the_sword");
        return this.attackDamage + (0.4d * (wayOfTheSword != null ? wayOfTheSword.getLevel() : 0));
    }

    private double getAttackSpeed(ItemStack stack, ICharacterStats stats) {
        if (stack.getEquipmentSlot() != EquipmentSlotType.MAINHAND) {
            return 0d;
        }
        SkillInfo iai = stats.getSkills().get("eposmajorum:iai");
        return this.attackSpeed - (0.4d * (iai != null ? iai.getLevel() : 0));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (stack.getEquipmentSlot() == EquipmentSlotType.MAINHAND) {
            Multimap<String, AttributeModifier> attributeModifiers = stack.getAttributeModifiers(stack.getEquipmentSlot());
            if (attributeModifiers.containsKey(SharedMonsterAttributes.ATTACK_DAMAGE.getName())) {
                UtilMethods.handleAttributes(attributeModifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()), "way_of_the_sword", new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "way_of_the_sword", getAttackDamage(stack, (ICharacterStats) player.getCapability(EposCapabilities.CHARACTER_STATS, null)), AttributeModifier.Operation.ADDITION));
            }
            if (attributeModifiers.containsKey(SharedMonsterAttributes.ATTACK_SPEED.getName())) {
                UtilMethods.handleAttributes(attributeModifiers.get(SharedMonsterAttributes.ATTACK_SPEED.getName()), "iai", new AttributeModifier(ATTACK_SPEED_MODIFIER, "iai", getAttackSpeed(stack, (ICharacterStats) player.getCapability(EposCapabilities.CHARACTER_STATS, null)), AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return 0;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}

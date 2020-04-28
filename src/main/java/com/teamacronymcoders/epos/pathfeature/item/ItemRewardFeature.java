package com.teamacronymcoders.epos.pathfeature.item;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.pathfeature.PathFeature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemRewardFeature extends PathFeature {
    private final String identifier;
    private final ItemStack stack;

    public ItemRewardFeature(String identifier, ItemStack stack) {
        super(new TranslationTextComponent("pathfeature.epos.itemreward.name", stack.getDisplayName()),
                new TranslationTextComponent("pathfeature.epos.itemreward.description", stack.getDisplayName()));
        this.identifier = identifier;
        this.stack = stack;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterStats characterStats) {
        World world = character.world;
        CompoundNBT entityNBT = character.getPersistentData();
        if (!entityNBT.contains("item_rewards")) {
            CompoundNBT newNBT = new CompoundNBT();
            character.getPersistentData().put("item_rewards", newNBT);
        }
        CompoundNBT itemRewards = entityNBT.getCompound("item_rewards");
        if (itemRewards.contains(identifier)) {
            itemRewards.putBoolean(identifier, true);
            BlockPos pos = character.getPosition();
            if (character instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) character;
                if (!player.addItemStackToInventory(stack)) {
                    ItemEntity entity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ());
                    entity.setItem(stack);
                    character.world.addEntity(entity);
                }
                return;
            }
            ItemEntity entity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ());
            entity.setItem(stack);
            character.world.addEntity(entity);
        }
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterStats characterStats) {}
}

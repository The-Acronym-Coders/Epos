package com.teamacronymcoders.epos.mixin;

import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.Set;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract ItemStack copy();

    @Unique
    private WeakReference<ServerPlayerEntity> playerRef = new WeakReference<>(null);

    @Inject(method = "hurt(ILjava/util/Random;Lnet/minecraft/entity/player/ServerPlayerEntity;)Z",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I"
            )
    )
    private void modifiedHurt(int damage, Random random, ServerPlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        playerRef = new WeakReference<>(playerEntity);
    }

    @ModifyVariable(
            method = "hurt(ILjava/util/Random;Lnet/minecraft/entity/player/ServerPlayerEntity;)Z",
            ordinal = 1,
            at = @At(
                value = "INVOKE_ASSIGN",
                target = "Lnet/minecraft/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I",
                shift = At.Shift.AFTER
            )
    )
    private int modifiedHurt(int level) {
        int modifiedLevel = level;
        ServerPlayerEntity playerEntity = playerRef.get();
        if (playerEntity != null) {
            modifiedLevel += this.getUnbreakingModifier();
            return modifiedLevel;
        }
        return level;
    }

    private int getUnbreakingModifier() {
        int unbreakingModifier = 0;
        ServerPlayerEntity playerEntity = playerRef.get();
        ItemStack stack = copy();
        if (playerEntity != null && (stack != null || stack.isEmpty())) {
            Set<ToolType> toolTypes = stack.getToolTypes();
            for (ToolType type : toolTypes) {
                switch (type.getName()) {
                    case "axe": {
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.AXE_SPECIALIZATION_NOVICE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.AXE_SPECIALIZATION_INTERMEDIATE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.AXE_SPECIALIZATION_ADVANCED) ? 1 : 0; }
                    case "hoe": {
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.HOE_SPECIALIZATION_NOVICE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.HOE_SPECIALIZATION_INTERMEDIATE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.HOE_SPECIALIZATION_ADVANCED) ? 1 : 0;
                    }
                    case "pickaxe": {
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.PICKAXE_SPECIALIZATION_NOVICE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.PICKAXE_SPECIALIZATION_INTERMEDIATE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.PICKAXE_SPECIALIZATION_ADVANCED) ? 1 : 0;
                    }
                    case "shovel": {
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.SHOVEL_SPECIALIZATION_NOVICE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.SHOVEL_SPECIALIZATION_INTERMEDIATE) ? 1 : 0;
                        unbreakingModifier += EposCharacterUtil.hasFeat(playerEntity, EposFeatIds.SHOVEL_SPECIALIZATION_ADVANCED) ? 1 : 0;
                    }
                }
            }
        }
        return unbreakingModifier;
    }
}

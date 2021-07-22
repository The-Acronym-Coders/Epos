package com.teamacronymcoders.epos.mixin;

import com.teamacronymcoders.epos.api.event.EposUnbreakingEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract ItemStack copy();

    @Unique
    private WeakReference<ServerPlayer> playerRef = new WeakReference<>(null);

    @Inject(method = "hurt(ILjava/util/Random;Lnet/minecraft/entity/player/ServerPlayerEntity;)Z",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/item/ItemStack;)I"
            )
    )
    private void modifiedHurt(int damage, Random random, ServerPlayer playerEntity, CallbackInfoReturnable<Boolean> cir) {
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
        ServerPlayer playerEntity = playerRef.get();
        if (playerEntity != null) {
            modifiedLevel += this.getUnbreakingModifier(level);
            return modifiedLevel;
        }
        return modifiedLevel;
    }

    @Unique
    private int getUnbreakingModifier(int originalLevel) {
        ServerPlayer playerEntity = playerRef.get();
        ItemStack stack = copy();
        if (playerEntity != null && (stack != null || stack.isEmpty())) {
            EposUnbreakingEvent event = new EposUnbreakingEvent(originalLevel, playerEntity, stack);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getModifiedLevel();
        }
        return originalLevel;
    }
}

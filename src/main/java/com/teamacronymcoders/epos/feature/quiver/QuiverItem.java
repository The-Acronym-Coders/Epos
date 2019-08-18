package com.teamacronymcoders.epos.feature.quiver;

import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.capability.PosInvHandlerCapabilityProvider;
import com.teamacronymcoders.epos.client.gui.QuiverGui;
import com.teamacronymcoders.epos.container.QuiverContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class QuiverItem extends Item {
    public QuiverItem() {
        super(new Item.Properties().group(Epos.EPOS_TAB).maxStackSize(1).rarity(Rarity.EPIC));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new PosInvHandlerCapabilityProvider();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->
                    NetworkHooks.openGui((ServerPlayerEntity) player, new QuiverGui(new QuiverContainer((PosInvHandler) handler, player.inventory), player.inventory, null)));
        }
        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    public PosInvHandler getHandler(ItemStack stack) {
        return (PosInvHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }

}

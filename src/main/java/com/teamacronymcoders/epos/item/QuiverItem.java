package com.teamacronymcoders.epos.item;

import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.capability.PosInvHandlerCapabilityProvider;
import com.teamacronymcoders.epos.container.item.QuiverContainer;
import com.teamacronymcoders.epos.utils.LazyOptionalHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QuiverItem extends Item implements INamedContainerProvider {
    public QuiverItem() {
        super(new Item.Properties()
                .group(Epos.EPOS_TAB)
                .maxStackSize(1)
                .rarity(Rarity.EPIC));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new PosInvHandlerCapabilityProvider(new PosInvHandler("", 0, 0, 9)
                .setInputFilter((idStack, integer) -> ShootableItem.ARROWS_OR_FIREWORKS.test(idStack)).setRange(3, 3));
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player instanceof ServerPlayerEntity) {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(quiverInv -> quiverInv instanceof PosInvHandler)
                    .ifPresent(capHandler -> {
                        PosInvHandler handler = ((PosInvHandler)capHandler);
                        NetworkHooks.openGui((ServerPlayerEntity) player, this,
                                packetBuffer -> {
                                    packetBuffer.writeString(handler.getName());
                                    packetBuffer.writeInt(handler.getXPos());
                                    packetBuffer.writeInt(handler.getYPos());
                                    packetBuffer.writeInt(handler.getSlots());
                                });
                    });
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("epos.container.quiver");
    }

    @Nullable
    @Override
    public Container createMenu(int num, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        return LazyOptionalHelper.of(entity.getHeldItem(entity.getActiveHand()), CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .toOptional()
                .filter(quiverInv -> quiverInv instanceof PosInvHandler)
                .map(quiverInv -> new QuiverContainer((PosInvHandler) quiverInv, inventory))
                .orElse(null);
    }
}

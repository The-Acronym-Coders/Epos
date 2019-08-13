package com.teamacronymcoders.epos.feature.quiver;

import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.client.gui.GuiQuiverAddonScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class QuiverItem extends Item {
    public QuiverItem() {
        super(new Item.Properties().group(Epos.EPOS_TAB).maxStackSize(1).rarity(Rarity.EPIC));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new QuiverCapabilityProvider(new PosInvHandler("", 0, 0, 9)
                .setInputFilter((idStack, integer) -> idStack.getItem().isIn(ItemTags.ARROWS) || idStack.getItem() instanceof FireworkRocketItem).setRange(3, 3));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getItem() instanceof QuiverItem) {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(capHandler -> playerIn.openContainer(new GuiQuiverAddonScreen((PosInvHandler) capHandler)));
            Epos.LOGGER.info("Boop");
        }
        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    private PosInvHandler getTrueHandler(IItemHandler handler) {
        return handler instanceof PosInvHandler ? (PosInvHandler) handler : null;
    }

    public PosInvHandler getHandler(ItemStack stack) {
        final PosInvHandler[] trueHandler = new PosInvHandler[1];
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> trueHandler[0] = getTrueHandler(handler));
        for (PosInvHandler handler : trueHandler) {
            return handler;
        }
        return null;
    }
}

package com.teamacronymcoders.epos.items;

import com.hrznstudio.titanium.Titanium;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.GuiAddonScreen;
import com.hrznstudio.titanium.client.gui.addon.SlotsGuiAddon;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.client.gui.GuiQuiverAddonScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class QuiverItem extends Item {
    @Save
    private final PosInvHandler inventory;

    public QuiverItem() {
        super(new Item.Properties().group(Epos.EPOS_TAB).maxStackSize(1).rarity(Rarity.EPIC));
        this.inventory = new PosInvHandler("", 0, 0, 9)
                .setInputFilter((stack, integer) -> stack.getItem() instanceof ArrowItem ||
                                                    stack.getItem() instanceof TippedArrowItem ||
                                                    stack.getItem() instanceof SpectralArrowItem ||
                                                    stack.getItem() instanceof FireworkRocketItem).setRange(3, 3);
        setRegistryName(EposAPI.ID, "quiver");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getItem() instanceof QuiverItem) {
            playerIn.openContainer(new GuiQuiverAddonScreen((QuiverItem) stack.getItem()));
            Epos.LOGGER.info("Boop");
        }
        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    public ItemStack getQuiverAmmo() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ArrowItem ||
                stack.getItem() instanceof TippedArrowItem ||
                stack.getItem() instanceof SpectralArrowItem ||
                stack.getItem() instanceof FireworkRocketItem) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    public PosInvHandler getInventory() {
        return inventory;
    }
}

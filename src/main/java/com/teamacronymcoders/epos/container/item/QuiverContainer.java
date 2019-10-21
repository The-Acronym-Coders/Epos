package com.teamacronymcoders.epos.container.item;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.teamacronymcoders.epos.feature.quiver.QuiverItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

import java.awt.*;

public class QuiverContainer extends Container {
    @ObjectHolder("epos:quiver_container")
    public static final ContainerType<QuiverContainer> TYPE = null;

    private PlayerInventory player;
    private boolean hasPlayerInventory;

    public QuiverContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(((QuiverItem) playerInventory.player.getHeldItemMainhand().getItem()).getHandler(playerInventory.player.getHeldItemMainhand()), playerInventory);
    }

    public QuiverContainer(PosInvHandler handler, PlayerInventory player) {
        super(TYPE, 0);
        this.player = player;
        int i = 0;
        for (int y = 0; y < handler.getYSize(); y++) {
            for (int x = 0; x < handler.getXSize(); x++) {
                addSlot(new SlotItemHandler(handler, i, handler.getXPos() + x * 18, handler.getYPos() + y * 18));
                i++;
            }
        }
        addPlayerChestInventory();
    }

    private void addPlayerChestInventory() {
        Point invPos = IAssetProvider.getAsset(IAssetProvider.DEFAULT_PROVIDER, AssetTypes.BACKGROUND).getInventoryPosition();
        if (hasPlayerInventory) return;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(player, j + i * 9 + 9, invPos.x + j * 18, invPos.y + i * 18));
            }
        }
        hasPlayerInventory = true;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}

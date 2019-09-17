package com.teamacronymcoders.epos.container;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.hrznstudio.titanium.container.impl.ContainerInventoryBase;
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

public class QuiverContainer extends ContainerInventoryBase {
    @ObjectHolder("epos:quiver_container")
    public static ContainerType<QuiverContainer> TYPE;
    private final PosInvHandler handler;
    private final PlayerInventory inventory;
    private boolean hasPlayerInventory;

    public QuiverContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        this(((QuiverItem) inventory.player.getHeldItemMainhand().getItem()).getHandler(inventory.player.getHeldItemMainhand()), inventory);
    }

    public QuiverContainer(PosInvHandler handler, PlayerInventory inventory) {
        super(TYPE, inventory, 0, IAssetProvider.DEFAULT_PROVIDER);
        this.handler = handler;
        this.inventory = inventory;
        int i = 0;
        for (int y = 0; y < handler.getYSize(); y++) {
            for (int x = 0; x < handler.getXSize(); x++) {
                addSlot(new SlotItemHandler(handler, i, handler.getXPos() + x * 18, handler.getYPos() + y * 18));
                i++;
            }
        }
    }

    public PosInvHandler getHandler() {
        return handler;
    }
}

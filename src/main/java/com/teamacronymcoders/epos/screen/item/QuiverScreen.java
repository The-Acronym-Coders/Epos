package com.teamacronymcoders.epos.screen.item;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.GuiAddonScreen;
import com.hrznstudio.titanium.client.gui.addon.SlotsGuiAddon;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.teamacronymcoders.epos.container.item.QuiverContainer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuiverScreen extends GuiAddonScreen implements IHasContainer<QuiverContainer> {
    private final PosInvHandler handler;
    private final QuiverContainer container;

    public QuiverScreen(QuiverContainer container) {
        super(IAssetProvider.DEFAULT_PROVIDER, true);
        this.handler = container.getPosInvHandler();
        this.container = container;
    }

    @Override
    public List<IFactory<IGuiAddon>> guiAddons() {
        List<IFactory<IGuiAddon>> addons = new ArrayList<>();
        addons.add(() -> new SlotsGuiAddon(handler));
        return addons;
    }

    @Override
    @Nonnull
    public QuiverContainer getContainer() {
        return container;
    }

    public static QuiverScreen create(QuiverContainer container, PlayerInventory playerInventory, ITextComponent displayName) {
        return new QuiverScreen(container);
    }
}

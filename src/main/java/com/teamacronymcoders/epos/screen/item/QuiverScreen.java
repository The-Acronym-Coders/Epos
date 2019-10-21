package com.teamacronymcoders.epos.screen.item;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.GuiAddonScreen;
import com.hrznstudio.titanium.client.gui.addon.SlotsGuiAddon;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.teamacronymcoders.epos.container.item.QuiverContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuiverScreen extends GuiAddonScreen implements INamedContainerProvider {

    private final PosInvHandler handler;

    public QuiverScreen(PosInvHandler handler) {
        super(IAssetProvider.DEFAULT_PROVIDER, true);
        this.handler = handler;
    }

    @Override
    public List<IFactory<IGuiAddon>> guiAddons() {
        List<IFactory<IGuiAddon>> addons = new ArrayList<>();
        addons.add(() -> new SlotsGuiAddon(handler));
        return addons;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("epos.container.quiver");
    }

    @Nullable
    @Override
    public Container createMenu(int num, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        return new QuiverContainer(num, inventory, null);
    }
}

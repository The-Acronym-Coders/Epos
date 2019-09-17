package com.teamacronymcoders.epos.gui;

import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.api.client.IGuiAddonProvider;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.hrznstudio.titanium.client.gui.container.GuiContainerBase;
import com.teamacronymcoders.epos.container.QuiverContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuiverGui extends GuiContainerBase<QuiverContainer> implements IHasContainer<QuiverContainer>, INamedContainerProvider {
    private final QuiverContainer container;

    public QuiverGui(QuiverContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, IAssetProvider.DEFAULT_PROVIDER);
        this.container = container;
    }

    @Override
    public ITextComponent getDisplayName() {
        return getTitle();
    }

    public QuiverGui setFactories(IGuiAddonProvider addonProvider) {
        List<IGuiAddon> list = new ArrayList<>();
        addonProvider.getGuiAddons().forEach(factory -> list.add(factory.create()));
        this.setAddons(list);
        return this;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity playerEntity) {
        return container;
    }
}

package com.teamacronymcoders.epos.client.gui;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.GuiAddonScreen;
import com.hrznstudio.titanium.client.gui.addon.SlotsGuiAddon;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.teamacronymcoders.epos.container.QuiverContainer;
import javafx.geometry.Pos;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GuiQuiverAddonScreen extends GuiAddonScreen implements INamedContainerProvider {
    private final QuiverContainer container;
    private PosInvHandler handler;

    public GuiQuiverAddonScreen(QuiverContainer quiverContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(IAssetProvider.DEFAULT_PROVIDER, true);
        this.container = quiverContainer;
    }

    @Override
    public List<IFactory<IGuiAddon>> guiAddons() {
        List<IFactory<IGuiAddon>> addons = new ArrayList<>();
        addons.add(() -> new SlotsGuiAddon(handler));
        return addons;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("epos.gui.quiver");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return container;
    }
}

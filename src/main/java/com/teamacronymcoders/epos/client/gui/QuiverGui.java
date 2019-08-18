package com.teamacronymcoders.epos.client.gui;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.client.gui.IGuiAddonConsumer;
import com.hrznstudio.titanium.client.gui.addon.ICanMouseDrag;
import com.hrznstudio.titanium.client.gui.addon.IClickable;
import com.hrznstudio.titanium.client.gui.addon.SlotsGuiAddon;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.mojang.blaze3d.platform.GlStateManager;
import com.teamacronymcoders.epos.container.QuiverContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuiverGui extends ContainerScreen<QuiverContainer> implements IGuiAddonConsumer, IHasContainer<QuiverContainer>, INamedContainerProvider {
    private final QuiverContainer container;
    private IAssetProvider assetProvider;
    private int x;
    private int y;
    private List<IGuiAddon> addons;

    private boolean isMouseDragging;
    private int dragX;
    private int dragY;

    public QuiverGui(QuiverContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, new TranslationTextComponent("gui.epos.quiver"));
        this.container = container;
        this.assetProvider = IAssetProvider.DEFAULT_PROVIDER;
        IAsset background = IAssetProvider.getAsset(assetProvider, AssetTypes.BACKGROUND);
        this.xSize = background.getArea().width;
        this.ySize = background.getArea().height;
        this.addons = new ArrayList<>();
        addons.add(new SlotsGuiAddon(container.getHandler()));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        GlStateManager.color4f(1, 1, 1, 1);
        getMinecraft().getTextureManager().bindTexture(IAssetProvider.getAsset(assetProvider, AssetTypes.BACKGROUND).getResourceLocation());
        blit(x, y, 0, 0, xSize, ySize);
        drawCenteredString(Minecraft.getInstance().fontRenderer, TextFormatting.GRAY + new TranslationTextComponent("gui.epos.quiver").getFormattedText(), x + xSize / 2, y + 6, 0xFFFFFF);
        this.checkForMouseDrag(mouseX, mouseY);
        addons.forEach(iGuiAddon -> iGuiAddon.drawGuiContainerBackgroundLayer(this, assetProvider, x, y, mouseX, mouseY, partialTicks));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        addons.forEach(iGuiAddon -> iGuiAddon.drawGuiContainerForegroundLayer(this, assetProvider, x, y, mouseX, mouseY));
        renderHoveredToolTip(mouseX - x, mouseY - y);
        for (IGuiAddon iGuiAddon : addons) {
            if (iGuiAddon.isInside(this, mouseX - x, mouseY - y) && !iGuiAddon.getTooltipLines().isEmpty()) {
                renderTooltip(iGuiAddon.getTooltipLines(), mouseX - x, mouseY - y);
            }
        }
    }

    private void checkForMouseDrag(int mouseX, int mouseY) {
        if (GLFW.glfwGetMouseButton(Minecraft.getInstance().mainWindow.getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
            this.isMouseDragging = true;
            for (IGuiAddon iGuiAddon : this.addons) {
                if (iGuiAddon instanceof ICanMouseDrag /*&& iGuiAddon.isInside(null, mouseX - x, mouseY - y)*/) {
                    ((ICanMouseDrag) iGuiAddon).drag(mouseX - dragX, mouseY - dragY);
                }
            }
        } else {
            this.isMouseDragging = false;
        }
        this.dragX = mouseX;
        this.dragY = mouseY;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        new ArrayList<>(addons).stream().filter(iGuiAddon -> iGuiAddon instanceof IClickable && iGuiAddon.isInside(this, mouseX - x, mouseY - y))
                .forEach(iGuiAddon -> ((IClickable) iGuiAddon).handleClick(this, x, y, mouseX, mouseY, mouseButton));
        return false;
    }

    public IAssetProvider getAssetProvider() {
        return assetProvider;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public QuiverContainer getContainer() {
        return container;
    }

    @Override
    public List<IGuiAddon> getAddons() {
        return addons;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.epos.quiver");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return container;
    }
}

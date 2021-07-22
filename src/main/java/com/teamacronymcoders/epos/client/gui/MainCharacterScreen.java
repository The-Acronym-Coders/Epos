package com.teamacronymcoders.epos.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.util.EposRenderingUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;

public class MainCharacterScreen extends AbstractContainerScreen<InventoryMenu> {

    private static final ResourceLocation background = new ResourceLocation(Epos.ID, "textures/gui/main_character_screen");

    private Player player;
    private float xMouse;
    private float yMouse;
    private boolean widthTooNarrow;
    private boolean buttonPressed;

    public MainCharacterScreen(Player player) {
        super(player.inventoryMenu, player.getInventory(), new TranslatableComponent("epos.gui.main.title"));
        this.player = player;
        this.passEvents = true;
    }

    @Override
    protected void init() {
        super.init();
        //this.widthTooNarrow = this.width < 379;

    }

    @Override
    public void render(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(poseStack);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(background);
        int xPos = this.leftPos;
        int yPos = this.topPos;
        this.blit(poseStack, xPos, yPos, 0, 0, this.imageWidth, this.imageHeight);
        EposRenderingUtil.renderEntityInInventory(xPos, yPos, 1.0F, pMouseX, pMouseY, this.player);
    }



    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (this.buttonPressed) {
            this.buttonPressed = false;
            return true;
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }
}

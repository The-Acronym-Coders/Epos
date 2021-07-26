package com.teamacronymcoders.epos.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.client.menu.MainCharacterMenu;
import com.teamacronymcoders.epos.client.widget.ButtonType;
import com.teamacronymcoders.epos.client.widget.OffsetButton;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import com.teamacronymcoders.epos.util.EposRenderingUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MainCharacterScreen extends AbstractContainerScreen<MainCharacterMenu> {

    public static final ResourceLocation BACKGROUND = new ResourceLocation(Epos.ID, "textures/gui/main_character_screen");

    @Nullable
    private Player player;
    private float xMouse;
    private float yMouse;
    private boolean widthTooNarrow;
    private boolean buttonPressed;

    public MainCharacterScreen(MainCharacterMenu screen, Inventory inventory, Component title) {
        super(screen, inventory, title);
        this.player = null;
        this.passEvents = true;
    }

    public MainCharacterScreen(@Nonnull Player player, int containerId) {
        super(new MainCharacterMenu(player, containerId), player.getInventory(), new TranslatableComponent("epos.gui.main.title"));
        this.player = player;
        this.passEvents = true;

        // Acquired Buttons
        this.addRenderableWidget(new OffsetButton(138, 19, "paths", ButtonType.THICK));
        this.addRenderableWidget(new OffsetButton(138, 46, "skills", ButtonType.THICK));
        this.addRenderableWidget(new OffsetButton(138, 73, "feats", ButtonType.THICK));

        // Point Shop
        this.addRenderableWidget(new OffsetButton(28, 80, "points", ButtonType.SKINNY));
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 198;
        this.imageHeight = 244;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partial) {
        super.render(poseStack, mouseX, mouseY, partial);
        this.xMouse = mouseX;
        this.yMouse = mouseY;
        ICharacterSheet sheet = EposCharacterUtil.getCharacterSheet(player);
        EposRenderingUtil.renderEntityInInventory(88, 19, 1.0F, mouseX, mouseY, this.player);
        if (sheet != null) {
            EposRenderingUtil.renderPointInfo(poseStack, 30, 21, sheet.getCharacterInfo().getPointInfo());
            EposRenderingUtil.renderExperienceBar(this, poseStack, 28, 13);
        }
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partial, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        this.blit(poseStack, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, 0, 0);
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

package com.teamacronymcoders.epos.client.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.epos.client.screen.MainCharacterScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;

public class OffsetButton extends Button {

    private final String identifier;
    private final ButtonType type;

    //TODO: Implement onPress
    public OffsetButton(int x, int y, String identifier, ButtonType type) {
        super(x, y, type.getWidth(), type.getHeight(), new TranslatableComponent("epos.button." + identifier), null);
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MainCharacterScreen.BACKGROUND);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, this.width, this.height, this.type.getU(isHovered()), this.type.getV(isHovered()));
        GuiComponent.drawCenteredString(poseStack, font, new TranslatableComponent("epos.button." + this.identifier), this.x + (this.width / 2), this.y + (this.height / 2), 0);
    }
}

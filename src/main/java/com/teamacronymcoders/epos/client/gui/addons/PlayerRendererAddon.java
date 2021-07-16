package com.teamacronymcoders.epos.client.gui.addons;

import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.teamacronymcoders.epos.client.gui.assets.EposAssets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;

public class PlayerRendererAddon extends BasicScreenAddon {

    private final PlayerEntity player;
    private final int scale;
    private final boolean renderBackground;
    private boolean renderedBackground = false;

    public PlayerRendererAddon(int posX, int posY, PlayerEntity player, int scale, boolean renderBackground) {
        super(posX, posY);
        this.player = player;
        this.scale = scale;
        this.renderBackground = renderBackground;
    }

    @Override
    public int getXSize() {
        return 51;
    }

    @Override
    public int getYSize() {
        return 72;
    }

    @Override
    public void drawBackgroundLayer(MatrixStack stack, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        IAsset asset = provider.getAsset(EposAssets.PLAYER_RENDERER_BACKGROUND_ASSET);
        if (asset != null && renderBackground) {
            Point offset = asset.getOffset();
            Rectangle area = asset.getArea();
            screen.getMinecraft().getTextureManager().bind(asset.getResourceLocation());
            screen.blit(stack, guiX + offset.x, guiY + offset.y + (area.height * scale), area.x, area.y, area.width * scale, area.height * scale);
            renderedBackground = true;
        }
    }

    @Override
    public void drawForegroundLayer(MatrixStack stack, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY) {
        renderEntityInInventory(this, mouseX, mouseY, player);
    }

    private void renderEntityInInventory(PlayerRendererAddon addon, float pMouseX, float pMouseY, PlayerEntity player) {
        float atanX = (float) Math.atan(pMouseX / 40.0F);
        float atanY = (float) Math.atan(pMouseY / 40.0F);
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)addon.getPosX(), (float)addon.getPosY(), 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.translate(0.0D, 0.0D, 1000.0D);
        matrixstack.scale((float)addon.getScale(), (float)addon.getScale(), (float)addon.getScale());
        Quaternion quaternionZPositive = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternionXPositive = Vector3f.XP.rotationDegrees(atanY * 20.0F);
        quaternionZPositive.mul(quaternionXPositive);
        matrixstack.mulPose(quaternionZPositive);
        float bodyRot = player.yBodyRot;
        float yRot = player.yRot;
        float xRot = player.xRot;
        float yHeadRotOld = player.yHeadRotO;
        float yHeadRot = player.yHeadRot;
        player.yBodyRot = 180.0F + atanX * 20.0F;
        player.yRot = 180.0F + atanX * 40.0F;
        player.xRot = -atanY * 20.0F;
        player.yHeadRot = player.yRot;
        player.yHeadRotO = player.yRot;
        EntityRendererManager manager = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternionXPositive.conj();
        manager.overrideCameraOrientation(quaternionXPositive);
        manager.setRenderShadow(false);
        IRenderTypeBuffer.Impl impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            manager.render(player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, impl, 15728880);
        });
        impl.endBatch();
        manager.setRenderShadow(true);
        player.yBodyRot = bodyRot;
        player.yRot = yRot;
        player.xRot = xRot;
        player.yHeadRotO = yHeadRotOld;
        player.yHeadRot = yHeadRot;
        RenderSystem.popMatrix();
    }

    public int getScale() {
        return scale;
    }
}

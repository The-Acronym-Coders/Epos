package com.teamacronymcoders.epos.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.teamacronymcoders.epos.api.character.info.PointInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EposRenderingUtil {

    public static void renderEntityInInventory(int xPos, int yPos, float scale, int xMouse, int yMouse, Player player) {
        renderEntityInInventory(xPos, yPos, scale, scale, scale, xMouse, yMouse, player);
    }

    public static void renderEntityInInventory(int xPos, int yPos, float xScale, float yScale, float zScale, float mouseX, float mouseY, LivingEntity livingEntity) {
        float atanX = (float)Math.atan(mouseX / 40.0F);
        float atanY = (float)Math.atan(mouseY / 40.0F);
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(xPos, yPos, 1050.0D);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack cleanStack = new PoseStack();
        cleanStack.translate(0.0D, 0.0D, 1000.0D);
        cleanStack.scale(xScale, yScale, zScale);
        Quaternion quaternionZP = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternionXP = Vector3f.XP.rotationDegrees(atanY * 20.0F);
        quaternionZP.mul(quaternionXP);
        cleanStack.mulPose(quaternionZP);
        float bodyRot = livingEntity.yBodyRot;
        float yRot = livingEntity.getYRot();
        float xRot = livingEntity.getXRot();
        float yHeadRotOld = livingEntity.yHeadRotO;
        float yHeadRot = livingEntity.yHeadRot;
        livingEntity.yBodyRot = 180.0F + atanX * 20.0F;
        livingEntity.setYRot(180.0F + atanX * 40.0F);
        livingEntity.setXRot(-atanY * 20.0F);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternionXP.conj();
        dispatcher.overrideCameraOrientation(quaternionXP);
        dispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            dispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, cleanStack, source, 15728880);
        });
        source.endBatch();
        dispatcher.setRenderShadow(true);
        livingEntity.yBodyRot = bodyRot;
        livingEntity.setYRot(yRot);
        livingEntity.setXRot(xRot);
        livingEntity.yHeadRotO = yHeadRotOld;
        livingEntity.yHeadRot = yHeadRot;
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    //TODO: Have @ChampionAsh look over how we should do the "getXPNeededForNextLevel" replacement and the math logic behind this.
    public static void renderExperienceBar(Screen screen, PoseStack poseStack, int xPos, int yPos) {
        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
        int i = screen.getMinecraft().player.getXpNeededForNextLevel();
        if (i > 0) {
            int k = (int)(screen.getMinecraft().player.experienceProgress * 183.0F);
            screen.blit(poseStack, xPos, yPos, 0, 64, 182, 5);
            if (k > 0) {
                screen.blit(poseStack, xPos, yPos, 0, 69, k, 5);
            }
        }
    }

    public static void renderPointInfo(PoseStack poseStack, int x, int y, PointInfo info) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        int pathPoints = info.getPathPoints();
        int skillPoints = info.getSkillPoints();
        int featPoints = info.getFeatPoints();

        // TODO: Figure out colors!
        GuiComponent.drawCenteredString(poseStack, font, Component.translatable("epos.points.path", pathPoints), x, y, 16777215);
        GuiComponent.drawCenteredString(poseStack, font, Component.translatable("epos.points.skill", skillPoints), x, y + 24, 16777215);
        GuiComponent.drawCenteredString(poseStack, font, Component.translatable("epos.points.feat", featPoints), x, y + 48, 16777215);

    }
}

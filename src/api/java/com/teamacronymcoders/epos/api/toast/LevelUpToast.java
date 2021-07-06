package com.teamacronymcoders.epos.api.toast;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class LevelUpToast implements IToast {

    private final IFormattableTextComponent message;
    private boolean playedSound;

    public LevelUpToast(ISkill skill, int newLevel) {
        if (skill != null) {
            this.message = new TranslationTextComponent("epos.levelUp.skill", skill.getName(), newLevel);
        } else {
            this.message = new TranslationTextComponent("epos.levelUp.skill.error");
        }
    }

    @Override
    public Visibility render(MatrixStack poseStack, ToastGui gui, long animationTime) {
        //TODO: Custom Toast Texture
        //TODO: Look over the code in this block :)
        gui.getMinecraft().getTextureManager().bind(TEXTURE);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        gui.blit(poseStack, 0, 0, 0, 0, this.width(), this.height());
        List<IReorderingProcessor> list = gui.getMinecraft().font.split(message, 125);
        if (list.size() == 1) {
            gui.getMinecraft().font.draw(poseStack, message, 30.0F, 7.0F, 16776960 | -16777216);
            gui.getMinecraft().font.draw(poseStack, list.get(0), 30.0F, 18.0F, -1);
        } else {
            int j = 1500;
            float f = 300.0F;
            if (animationTime < j) {
                int k = MathHelper.floor(MathHelper.clamp((float) (j - animationTime) / f, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                gui.getMinecraft().font.draw(poseStack, message, 30.0F, 11.0F, 16776960 | k);
            } else {
                int i1 = MathHelper.floor(MathHelper.clamp((float) (animationTime - j) / f, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                int l = this.height() / 2 - list.size() * 9 / 2;

                for (IReorderingProcessor ireorderingprocessor : list) {
                    gui.getMinecraft().font.draw(poseStack, ireorderingprocessor, 30.0F, (float) l, 16777215 | i1);
                    l += 9;
                }
            }
        }

        if (!this.playedSound && animationTime > 0L) {
            this.playedSound = true;
            //TODO: Create custom SoundEvent for Toast
            //gui.getMinecraft().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
        }

        //TODO: Add method to display Skill Icon
        //gui.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(skill.getIcon, 8, 8);
        return animationTime >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }

}

package com.teamacronymcoders.epos.api.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.List;

public class LevelUpToast implements Toast {

    private final MutableComponent message;
    private boolean playedSound;

    public LevelUpToast(ISkill skill, int newLevel) {
        if (skill != null) {
            this.message = Component.translatable("epos.levelUp.skill", skill.getName(), newLevel);
        } else {
            this.message = Component.translatable("epos.levelUp.skill.error");
        }
    }

    @Override
    public Toast.Visibility render(PoseStack poseStack, ToastComponent component, long animationTime) {
        //TODO: Custom Toast Texture
        //TODO: Look over the code in this block :)
        component.getMinecraft().getTextureManager().bindForSetup(TEXTURE);
        RenderSystem.setShaderFogColor(1.0F, 1.0F, 1.0F);
        component.blit(poseStack, 0, 0, 0, 0, this.width(), this.height());
        List<FormattedCharSequence> list = component.getMinecraft().font.split(message, 125);
        if (list.size() == 1) {
            component.getMinecraft().font.draw(poseStack, message, 30.0F, 7.0F, 16776960 | -16777216);
            component.getMinecraft().font.draw(poseStack, list.get(0), 30.0F, 18.0F, -1);
        } else {
            int j = 1500;
            float f = 300.0F;
            if (animationTime < j) {
                int k = Mth.floor(Mth.clamp((float) (j - animationTime) / f, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                component.getMinecraft().font.draw(poseStack, message, 30.0F, 11.0F, 16776960 | k);
            } else {
                int i1 = Mth.floor(Mth.clamp((float) (animationTime - j) / f, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                int l = this.height() / 2 - list.size() * 9 / 2;

                for (FormattedCharSequence formattedCharSequence : list) {
                    component.getMinecraft().font.draw(poseStack, formattedCharSequence, 30.0F, (float) l, 16777215 | i1);
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
        return animationTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

}

package com.teamacronymcoders.epos.client.widget.scroll;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraftforge.client.gui.ScrollPanel;

public class InfoPanel extends ScrollPanel {

    public InfoPanel(Minecraft client, int width, int height, int top, int left) {
        super(client, width, height, top, left);
    }

    @Override
    protected int getContentHeight() {
        return 0;
    }

    @Override
    protected void drawPanel(PoseStack mStack, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {

    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}

package com.teamacronymcoders.epos.client.widget.scroll;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.PathInfo;
import com.teamacronymcoders.epos.path.Path;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.Optional;

public class ListWidget<T extends ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {

    public ListWidget(Minecraft p_94442_, int p_94443_, int p_94444_, int p_94445_, int p_94446_, int p_94447_) {
        super(p_94442_, p_94443_, p_94444_, p_94445_, p_94446_, p_94447_);
    }

    @Override
    public Optional<GuiEventListener> getChildAt(double pMouseX, double pMouseY) {
        return super.getChildAt(pMouseX, pMouseY);
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        super.mouseMoved(pMouseX, pMouseY);
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        return super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        return super.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public void setInitialFocus(@Nullable GuiEventListener pEventListener) {
        super.setInitialFocus(pEventListener);
    }

    @Override
    public void magicalSpecialHackyFocus(@Nullable GuiEventListener pEventListener) {
        super.magicalSpecialHackyFocus(pEventListener);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    public class PathEntry extends ObjectSelectionList.Entry<PathEntry> {

        private final IPath parent;
        private final PathInfo info;
        private final Screen parentScreen;

        PathEntry(IPath parent, PathInfo info, Screen parentScreen) {
            this.parent = parent;
            this.info = info;
            this.parentScreen = parentScreen;
        }

        @Override
        public Component getNarration() {
            return null;
        }

        @Override
        public void render(PoseStack pMatrixStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTicks) {

        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            return super.mouseClicked(pMouseX, pMouseY, pButton);
        }

        public IPath getParent() {
            return parent;
        }

        public PathInfo getInfo() {
            return info;
        }
    }
}

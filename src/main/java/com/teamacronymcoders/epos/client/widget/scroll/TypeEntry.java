package com.teamacronymcoders.epos.client.widget.scroll;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.PathInfo;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.client.screen.purchase.PurchaseScreen;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public abstract class TypeEntry<T extends IDescribable> extends ObjectSelectionList.Entry<TypeEntry<T>> {

    private final PurchaseScreen<T> parentScreen;

    public TypeEntry(PurchaseScreen<T> parentScreen) {
        this.parentScreen = parentScreen;
    }

    public PurchaseScreen<T> getParentScreen() {
        return parentScreen;
    }

    public static class PathEntry extends TypeEntry<IPath> {

        private final IPath path;
        private final PathInfo info;

        public PathEntry(PurchaseScreen<IPath> parentScreen, IPath path, PathInfo info) {
            super(parentScreen);
            this.path = path;
            this.info = info;
        }

        @Override
        public Component getNarration() {
            return Component.translatable("narrator.select", path.getName());
        }

        @Override
        public void render(PoseStack pMatrixStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTicks) {

        }
    }

    public static class SkillEntry extends TypeEntry<ISkill> {

        private final ISkill skill;
        private final SkillInfo info;

        public SkillEntry(PurchaseScreen<ISkill> parentScreen, ISkill skill, SkillInfo info) {
            super(parentScreen);
            this.skill = skill;
            this.info = info;
        }

        @Override
        public Component getNarration() {
            return Component.translatable("narrator.select", skill.getName());
        }

        @Override
        public void render(PoseStack pMatrixStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTicks) {

        }
    }

    public static class FeatEntry extends TypeEntry<IFeat> {

        private final IFeat feat;
        private final FeatInfo info;

        public FeatEntry(PurchaseScreen<IFeat> parentScreen, IFeat feat, FeatInfo info) {
            super(parentScreen);
            this.feat = feat;
            this.info = info;
        }

        @Override
        public Component getNarration() {
            return Component.translatable("narrator.select", feat.getName());
        }

        @Override
        public void render(PoseStack pMatrixStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTicks) {

        }
    }
}

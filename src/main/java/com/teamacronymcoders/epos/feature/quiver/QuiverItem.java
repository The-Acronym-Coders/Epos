package com.teamacronymcoders.epos.feature.quiver;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.GuiAddonScreen;
import com.hrznstudio.titanium.client.gui.IGuiAddonConsumer;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import com.mojang.blaze3d.platform.GlStateManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.capability.PosInvHandlerCapabilityProvider;
import com.teamacronymcoders.epos.client.gui.GuiQuiverAddonScreen;
import com.teamacronymcoders.epos.container.QuiverContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class QuiverItem extends Item {
    public QuiverItem() {
        super(new Item.Properties().group(Epos.EPOS_TAB).maxStackSize(1).rarity(Rarity.EPIC));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new PosInvHandlerCapabilityProvider();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->
                    new NewGuiQuiverAddonScreen(new QuiverContainer((PosInvHandler) handler, player.inventory)));
        }
        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    public PosInvHandler getHandler(ItemStack stack) {
        return (PosInvHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }

    public static class NewGuiQuiverAddonScreen extends ContainerScreen<QuiverContainer> implements IGuiAddonConsumer, IHasContainer<QuiverContainer> {
        private final QuiverContainer quiverContainer;
        private IAssetProvider assetProvider;
        private int x;
        private int y;
        private List<IGuiAddon> addonList;

        private boolean isMouseDragging;
        private int dragX;
        private int dragY;

        public NewGuiQuiverAddonScreen(QuiverContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
            super(screenContainer, inv, titleIn);
        }

        @Override
        public List<IGuiAddon> getAddons() {
            return null;
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
            this.renderBackground();
            x = (width - xSize) / 2;
            y = (height - ySize) / 2;
            GlStateManager.color4f(1, 1, 1, 1);
            getMinecraft().getTextureManager().bindTexture(IAssetProvider.getAsset(assetProvider, AssetTypes.BACKGROUND).getResourceLocation());
            blit(x, y, 0, 0, xSize, ySize);
            drawCenteredString(Minecraft.getInstance().fontRenderer, TextFormatting.GRAY + new TranslationTextComponent(containerTileBase.getTile().getBlockState().getBlock().getTranslationKey()).getFormattedText(), x + xSize / 2, y + 6, 0xFFFFFF);
            this.checkForMouseDrag(mouseX, mouseY);
            addonList.forEach(iGuiAddon -> iGuiAddon.drawGuiContainerBackgroundLayer(this, assetProvider, x, y, mouseX, mouseY, partialTicks));
            container.updateSlotPosition();
        }

        private void checkForMouseDrag(int mouseX, int mouseY) {
            if (GLFW.glfwGetMouseButton(Minecraft.getInstance().mainWindow.getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
                this.isMouseDragging = true;
                for (IGuiAddon iGuiAddon : this.addonList) {
                    if (iGuiAddon instanceof ICanMouseDrag /*&& iGuiAddon.isInside(null, mouseX - x, mouseY - y)*/) {
                        ((ICanMouseDrag) iGuiAddon).drag(mouseX - dragX, mouseY - dragY);
                    }
                }
            } else {
                this.isMouseDragging = false;
            }
            this.dragX = mouseX;
            this.dragY = mouseY;
        }
    }
}

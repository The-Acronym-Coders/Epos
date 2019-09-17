package com.teamacronymcoders.epos.feature.quiver;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.api.client.IGuiAddonProvider;
import com.hrznstudio.titanium.block.tile.inventory.PosInvHandler;
import com.hrznstudio.titanium.client.gui.addon.SlotsGuiAddon;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.capability.PosInvHandlerCapabilityProvider;
import com.teamacronymcoders.epos.gui.QuiverGui;
import com.teamacronymcoders.epos.container.QuiverContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShootableItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuiverItem extends Item implements IGuiAddonProvider {
    private List<IFactory<? extends IGuiAddon>> factories = new ArrayList<>();

    public QuiverItem() {
        super(new Item.Properties().group(Epos.EPOS_TAB).maxStackSize(1).rarity(Rarity.EPIC));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new PosInvHandlerCapabilityProvider(new PosInvHandler("quiver", 62, 30, 9)
                .setInputFilter((idStack, integer) -> ShootableItem.ARROWS_OR_FIREWORKS.test(idStack))
                .setRange(3, 3));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                if (!factories.isEmpty()) {
                    factories.clear();
                }
                factories.add(() -> new SlotsGuiAddon((PosInvHandler) handler));
                NetworkHooks.openGui((ServerPlayerEntity) player, new QuiverGui(new QuiverContainer((PosInvHandler) handler, player.inventory), player.inventory, new TranslationTextComponent("gui.epos.quiver")).setFactories(this));
            });
        }
        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    public PosInvHandler getHandler(ItemStack stack) {
        return (PosInvHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }

    @Override
    public List<IFactory<? extends IGuiAddon>> getGuiAddons() {
        return factories;
    }
}

package com.teamacronymcoders.epos.client.menu;

import com.mojang.datafixers.util.Pair;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.client.menu.type.EposMenuTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import javax.annotation.Nonnull;

import static net.minecraft.world.inventory.InventoryMenu.*;

public class MainCharacterMenu extends AbstractContainerMenu {

    private static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    private final Player player;
    private final Container container;

    public MainCharacterMenu(int containerId) {
        super(EposMenuTypes.MAIN_CHARACTER_MENU_TYPE.get(), containerId);
        this.player = null;
        this.container = null;
    }

    public MainCharacterMenu(@Nonnull Player player, int containerId) {
        super(EposMenuTypes.MAIN_CHARACTER_MENU_TYPE.get(), containerId);
        this.player = player;
        this.container = player.getInventory();

        int x;
        int y;

        // Player Inventory Slots (Slot: 0-27)
        for(x = 0; x < 3; ++x) {
            for(y = 0; y < 9; ++y) {
                this.addSlot(new Slot(container, x + y, 8 + y * 18, 103 + x * 18));
                Epos.getLogger().info("Created Slot: " + (x + y));
            }
        }

        // Hotbar Slots (Slot: 28-36)
        for(x = 0; x < 9; ++x) {
            this.addSlot(new Slot(container, x + 27, 8 + x * 18, 161));
            Epos.getLogger().info("Created Slot: " + (x + 27));
        }

        // Armor Slots (Slot: 37-40)
        for(x = 0; x < 4; ++x) {
            final EquipmentSlot equipmentslot = SLOT_IDS[x];
            Epos.getLogger().info("Created Slot: " + (x + 36));
            this.addSlot(new Slot(container, x + 36, 8, 8 + x * 18) {
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in
                 * the case of armor slots)
                 */
                public int getMaxStackSize() {
                    return 1;
                }

                /**
                 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
                 */
                public boolean mayPlace(ItemStack stack) {
                    return stack.canEquip(equipmentslot, player);
                }

                /**
                 * Return whether this slot's stack can be taken from this slot.
                 */
                public boolean mayPickup(Player player) {
                    ItemStack itemstack = this.getItem();
                    return (itemstack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.mayPickup(player);
                }
            });
        }

        // Shield Slot (Slot: 41)
        this.addSlot(new Slot(container, 41, 8, 13 * 18) {
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Nonnull
    public Player getPlayer() {
        return player;
    }

    @Nonnull
    public Container getContainer() {
        return container;
    }

}

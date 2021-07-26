package com.teamacronymcoders.epos.client.menu.type;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.client.menu.MainCharacterMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EposMenuTypes {

    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, Epos.ID);

    public static final RegistryObject<MenuType<MainCharacterMenu>> MAIN_CHARACTER_MENU_TYPE = create("main_character_menu", ((containerId, inventory) -> new MainCharacterMenu(containerId)));


    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> create(String name, MenuType.MenuSupplier<T> pFactory) {
        return MENU_TYPES.register(name, () -> new MenuType<>(pFactory));
    }

    public EposMenuTypes(IEventBus modBus) {
        MENU_TYPES.register(modBus);
    }

}

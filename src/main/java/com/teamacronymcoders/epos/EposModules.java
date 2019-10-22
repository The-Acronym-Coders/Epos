package com.teamacronymcoders.epos;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.container.item.QuiverContainer;
import com.teamacronymcoders.epos.item.QuiverItem;
import com.teamacronymcoders.epos.screen.item.QuiverScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class EposModules {
    public static final Module.Builder QUIVER =
            Module.builder("quiver")
                    .force()
                    .description("Adds The Quiver")
                    .feature(
                            Feature.builder("quiver")
                                    .content(Item.class, new QuiverItem().setRegistryName(EposAPI.ID, "quiver"))
                                    .event(
                                            EventManager.mod(FMLClientSetupEvent.class)
                                                .process(event -> ScreenManager.registerFactory(QuiverContainer.TYPE, QuiverScreen::create))
                                    )
                    );
}

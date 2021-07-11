package com.teamacronymcoders.epos.impl;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.registry.builder.FeatBuilder;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class EposFeats {
    private static final IFeat test = FeatBuilder.create(Epos.instance().getRegistrate(), Epos.instance().getRegistrate(), "test", null)
            .name(() -> new StringTextComponent("test"))
            .description(() -> new StringTextComponent("testDescription"))
            .withEventManagers(
                    EventManager.create(FMLLoadCompleteEvent.class, EventManager.Bus.FORGE).process(event -> Epos.getLogger().info("Boop"))
            ).createEntry();
}

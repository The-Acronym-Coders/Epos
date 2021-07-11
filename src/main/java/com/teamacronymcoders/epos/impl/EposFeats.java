package com.teamacronymcoders.epos.impl;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.builder.FeatBuilder;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.impl.feat.miner.CascadingExcavations;
import com.teamacronymcoders.epos.impl.feat.fisherman.ExperiencedAngler;
import com.teamacronymcoders.epos.impl.feat.lumberjack.Timber;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.SpiritOfBattle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class EposFeats {
    private static final IFeat test = FeatBuilder.create(new ResourceLocation(Epos.ID, "test"))
            .name(() -> new StringTextComponent("test"))
            .description(() -> new StringTextComponent("testDescription"))
            .withEventManagers(
                    EventManager.create(FMLLoadCompleteEvent.class, EventManager.Bus.FORGE).process(event -> Epos.getLogger().info("Boop"))
            ).createEntry();

    public static void registerEventManagers() {
        CascadingExcavations.featManager.subscribe();
        ExperiencedAngler.featManager.subscribe();
        SpiritOfBattle.featManager.subscribe();
        Timber.featManager.subscribe();
    }
}

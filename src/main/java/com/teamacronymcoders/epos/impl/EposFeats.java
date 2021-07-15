package com.teamacronymcoders.epos.impl;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.builder.FeatBuilder;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.impl.feat.farmer.EffectiveCultivation;
import com.teamacronymcoders.epos.impl.feat.farmer.FamiliarAdditions;
import com.teamacronymcoders.epos.impl.feat.ranger.AgileCombatant;
import com.teamacronymcoders.epos.impl.feat.generic.Specialized;
import com.teamacronymcoders.epos.impl.feat.miner.CascadingExcavations;
import com.teamacronymcoders.epos.impl.feat.fisherman.ExperiencedAngler;
import com.teamacronymcoders.epos.impl.feat.lumberjack.Timber;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.SpiritOfBattle;
import com.teamacronymcoders.epos.impl.feat.miner.EffectiveMining;
import com.teamacronymcoders.epos.impl.feat.monk.EmbraceOfTheLotus;
import com.teamacronymcoders.epos.impl.feat.monk.FistOfCrumblingEarth;
import com.teamacronymcoders.epos.impl.feat.ranger.MansBestFriend;
import com.teamacronymcoders.epos.impl.feat.ranger.PackMentality;
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
        AgileCombatant.registerFeatManagers();
        CascadingExcavations.featManager.subscribe();
        EffectiveCultivation.registerFeatManagers();
        EffectiveMining.featManager.subscribe();
        EmbraceOfTheLotus.registerFeatManagers();
        ExperiencedAngler.featManager.subscribe();
        FamiliarAdditions.registerFeatManagers();
        FistOfCrumblingEarth.registerFeatManagers();
        MansBestFriend.registerFeatManagers();
        PackMentality.featManager.subscribe();
        Specialized.featManager.subscribe();
        SpiritOfBattle.featManager.subscribe();
        Timber.featManager.subscribe();
    }
}

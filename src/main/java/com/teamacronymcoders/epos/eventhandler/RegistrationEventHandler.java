package com.teamacronymcoders.epos.eventhandler;

import com.google.common.collect.Lists;
<<<<<<< cb35bacfea2792459db9e29d3a9eaac40d952883:src/main/java/com/teamacronymcoders/epos/eventhandler/RegistrationEventHandler.java
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.content.*;
import com.teamacronymcoders.epos.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.skillxp.SkillXPFeatureProvider;
=======
import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.IFeat;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.eposmajorum.api.registry.RegistrationEvent;
import com.teamacronymcoders.eposmajorum.content.monk.UnarmedStrike;
import com.teamacronymcoders.eposmajorum.content.shared.GluttonousHunger;
import com.teamacronymcoders.eposmajorum.content.shared.SpiritOfBattle;
import com.teamacronymcoders.eposmajorum.content.warrior.EnderResistance;
import com.teamacronymcoders.eposmajorum.content.warrior.WayOfTheBlade;
import com.teamacronymcoders.eposmajorum.content.worker.ImprovisedCombat;
import com.teamacronymcoders.eposmajorum.content.worker.LuckyLure;
import com.teamacronymcoders.eposmajorum.content.worker.ObsidianSmasher;
import com.teamacronymcoders.eposmajorum.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.eposmajorum.pathfeature.skillxp.SkillXPFeatureProvider;
>>>>>>> Started tinkering with more content:src/main/java/com/teamacronymcoders/eposmajorum/eventhandler/RegistrationEventHandler.java
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = EposAPI.ID)
public class RegistrationEventHandler {
    @SubscribeEvent
    public static void registerFeats(RegistrationEvent<IFeat> featRegistryEvent) {
        featRegistryEvent.register(Lists.newArrayList(
                UnarmedStrike.FEAT,
                SpiritOfBattle.FEAT,
                LuckyLure.FEAT,
                EnderResistance.FEAT,
                ObsidianSmasher.FEAT,
                GluttonousHunger.FEAT,
                WayOfTheBlade.FEAT,
                ImprovisedCombat.FEAT
        ));
    }

    @SubscribeEvent
    public static void registerPathFeatureProviders(
            RegistrationEvent<IPathFeatureProvider> pathFeatureProviderRegistryEvent) {
        pathFeatureProviderRegistryEvent.register(Lists.newArrayList(
                new FeatFeatureProvider(),
                new SkillXPFeatureProvider()
        ));
    }
}

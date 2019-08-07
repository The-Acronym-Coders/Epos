package com.teamacronymcoders.mcrpg.eventhandler;

import com.google.common.collect.Lists;
import com.teamacronymcoders.mcrpg.api.EposAPI;
import com.teamacronymcoders.mcrpg.api.feat.IFeat;
import com.teamacronymcoders.mcrpg.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.mcrpg.api.registry.RegistrationEvent;
import com.teamacronymcoders.mcrpg.content.*;
import com.teamacronymcoders.mcrpg.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.mcrpg.pathfeature.skillxp.SkillXPFeatureProvider;
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
                GluttonousHunger.FEAT
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

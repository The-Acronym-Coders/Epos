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
import com.teamacronymcoders.eposmajorum.content.monk.UnarmedStrikeSkill;
import com.teamacronymcoders.eposmajorum.content.shared.GluttonousHungerFeat;
import com.teamacronymcoders.eposmajorum.content.shared.SpiritOfBattleFeat;
import com.teamacronymcoders.eposmajorum.content.warrior.EnderResistanceFeat;
import com.teamacronymcoders.eposmajorum.content.warrior.WayOfTheBladeFeat;
import com.teamacronymcoders.eposmajorum.content.shared.ImprovisedCombatFeat;
import com.teamacronymcoders.eposmajorum.content.fisherman.LuckyLureFeat;
import com.teamacronymcoders.eposmajorum.content.miner.ObsidianSmasherFeat;
import com.teamacronymcoders.eposmajorum.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.eposmajorum.pathfeature.levelupskill.LevelUpSkillFeatureProvider;
import com.teamacronymcoders.eposmajorum.pathfeature.skillxp.SkillXPFeatureProvider;
>>>>>>> Started tinkering with more content:src/main/java/com/teamacronymcoders/eposmajorum/eventhandler/RegistrationEventHandler.java
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = EposAPI.ID)
public class RegistrationEventHandler {
    @SubscribeEvent
    public static void registerFeats(RegistrationEvent<IFeat> featRegistryEvent) {
        featRegistryEvent.register(Lists.newArrayList(
                UnarmedStrikeSkill.FEAT,
                SpiritOfBattleFeat.FEAT,
                LuckyLureFeat.FEAT,
                EnderResistanceFeat.FEAT,
                ObsidianSmasherFeat.FEAT,
                GluttonousHungerFeat.FEAT,
                WayOfTheBladeFeat.FEAT,
                ImprovisedCombatFeat.FEAT
        ));
    }

    @SubscribeEvent
    public static void registerPathFeatureProviders(
            RegistrationEvent<IPathFeatureProvider> pathFeatureProviderRegistryEvent) {
        pathFeatureProviderRegistryEvent.register(Lists.newArrayList(
                new FeatFeatureProvider(),
                new SkillXPFeatureProvider(),
                new LevelUpSkillFeatureProvider()
        ));
    }
}

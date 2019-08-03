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
import com.teamacronymcoders.eposmajorum.content.feats.defence.EnderResistanceFeat;
import com.teamacronymcoders.eposmajorum.content.feats.offence.SpiritOfBattleFeat;
import com.teamacronymcoders.eposmajorum.content.feats.utility.*;
import com.teamacronymcoders.eposmajorum.content.skills.offence.ImprovisedCombatSkill;
import com.teamacronymcoders.eposmajorum.content.skills.offence.UnarmedStrikeSkill;
import com.teamacronymcoders.eposmajorum.content.skills.offence.WayOfTheBladeSkill;
import com.teamacronymcoders.eposmajorum.content.skills.utility.*;
import com.teamacronymcoders.eposmajorum.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.eposmajorum.pathfeature.feat.FeatPointFeatureProvider;
import com.teamacronymcoders.eposmajorum.pathfeature.item.ItemRewardFeatureProvider;
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
                EnderResistanceFeat.FEAT,
                ImprovisedCombatSkill.FEAT,
                SpiritOfBattleFeat.FEAT,
                UnarmedStrikeSkill.FEAT,
                WayOfTheBladeSkill.FEAT,
                GluttonousHungerFeat.FEAT,
                GravelExcavatorFeat.FEAT,
                HarvestAreaSkill.FEAT,
                LuckyAnglerSkill.FEAT,
                LumberjacksEfficiencySkill.FEAT,
                MinersEfficiencySkill.FEAT,
                ObsidianSmasherFeat.TOOL_FEAT,
                ObsidianSmasherFeat.NO_TOOL_FEAT,
                OreExtractionFeat.FEAT,
                PalmOfExcavationSkill.FEAT,
                PurityFeats.PURITY,
                PurityFeats.DIAMOND,
                TimberFeat.FEAT
        ));
    }

    @SubscribeEvent
    public static void registerPathFeatureProviders(
            RegistrationEvent<IPathFeatureProvider> pathFeatureProviderRegistryEvent) {
        pathFeatureProviderRegistryEvent.register(Lists.newArrayList(
                new FeatFeatureProvider(),
                new SkillXPFeatureProvider(),
                new LevelUpSkillFeatureProvider(),
                new ItemRewardFeatureProvider(),
                new FeatPointFeatureProvider()
        ));
    }

}

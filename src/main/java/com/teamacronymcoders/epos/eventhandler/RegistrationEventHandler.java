package com.teamacronymcoders.epos.eventhandler;

import com.google.common.collect.Lists;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.feats.*;
import com.teamacronymcoders.epos.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.skillxp.SkillXPFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.feat.FeatPointFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.item.ItemRewardFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.levelupskill.LevelUpSkillFeatureProvider;
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
    public static void registerPathFeatureProviders(RegistrationEvent<IPathFeatureProvider> pathFeatureProviderRegistryEvent) {
        pathFeatureProviderRegistryEvent.register(Lists.newArrayList(
                new FeatFeatureProvider(),
                new SkillXPFeatureProvider(),
                new LevelUpSkillFeatureProvider(),
                new ItemRewardFeatureProvider(),
                new FeatPointFeatureProvider()
        ));
    }

}

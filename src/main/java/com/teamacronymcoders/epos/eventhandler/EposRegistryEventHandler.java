package com.teamacronymcoders.epos.eventhandler;

import com.google.common.collect.Lists;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.feats.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EposRegistryEventHandler {

    @SubscribeEvent
    public static void registerFeats(RegistrationEvent<IFeat> featRegistryEvent) {
        featRegistryEvent.register(Lists.newArrayList(
                EnderResistanceFeat.FEAT,
                ImprovisedCombatFeat.FEAT,
                SpiritOfBattleFeat.FEAT,
                UnarmedStrikeFeat.FEAT,
                WayOfTheBladeFeat.FEAT,
                GluttonousHungerFeat.FEAT,
                GravelExcavatorFeat.FEAT,
                HarvestAreaFeat.FEAT,
                LuckyAnglerFeat.FEAT,
                EfficiencyFeats.LUMBERJACK_FEAT,
                EfficiencyFeats.MINER_FEAT,
                ObsidianSmasherFeat.TOOL_FEAT,
                ObsidianSmasherFeat.NO_TOOL_FEAT,
                OreExtractionFeat.FEAT,
                PalmOfExcavationFeat.FEAT,
                PurityFeats.PURITY,
                PurityFeats.DIAMOND,
                TimberFeat.FEAT
        ));
    }
}

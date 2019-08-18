package com.teamacronymcoders.epos.eventhandler;

import com.google.common.collect.Lists;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.container.QuiverContainer;
import com.teamacronymcoders.epos.feats.*;
import com.teamacronymcoders.epos.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.item.ItemRewardFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.levelupskill.LevelUpSkillFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.skillxp.SkillXPFeatureProvider;
import com.teamacronymcoders.epos.sounds.EposSoundEvents;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import static com.teamacronymcoders.epos.api.EposAPI.ID;

@EventBusSubscriber(modid = EposAPI.ID, bus = EventBusSubscriber.Bus.MOD)
public class RegistrationEventHandler {
    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                IForgeContainerType.create(QuiverContainer::new).setRegistryName(new ResourceLocation(ID, "quiver_container"))
        );
    }

    @SubscribeEvent
    public static void registerSound(RegistryEvent.Register<SoundEvent> eventRegistryEvent) {
        eventRegistryEvent.getRegistry().registerAll(
                EposSoundEvents.levelUp
        );
    }

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

    @SubscribeEvent
    public static void registerPathFeatureProviders(RegistrationEvent<IPathFeatureProvider> pathFeatureProviderRegistryEvent) {
        pathFeatureProviderRegistryEvent.register(Lists.newArrayList(
                new FeatFeatureProvider(),
                new SkillXPFeatureProvider(),
                new LevelUpSkillFeatureProvider(),
                new ItemRewardFeatureProvider()
        ));
    }
}

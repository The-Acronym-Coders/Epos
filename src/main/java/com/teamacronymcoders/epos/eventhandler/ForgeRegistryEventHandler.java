package com.teamacronymcoders.epos.eventhandler;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatureProvider;
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
import net.minecraftforge.registries.RegistryBuilder;

import static com.teamacronymcoders.epos.api.EposAPI.ID;

@EventBusSubscriber(modid = EposAPI.ID, bus = EventBusSubscriber.Bus.MOD)
public class ForgeRegistryEventHandler {
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
    public static void registerPathFeatureProviders(RegistryEvent.Register<PathFeatureProvider> pathFeatureProviderRegistryEvent) {
        pathFeatureProviderRegistryEvent.getRegistry().registerAll(
                new FeatFeatureProvider().setRegistryName(new ResourceLocation(ID, "feat")),
                new SkillXPFeatureProvider().setRegistryName(new ResourceLocation(ID, "skill_xp")),
                new LevelUpSkillFeatureProvider().setRegistryName(new ResourceLocation(ID, "skill_level")),
                new ItemRewardFeatureProvider().setRegistryName(new ResourceLocation(ID, "item_reward"))
        );
    }

    @SubscribeEvent
    public static void registerFeats(RegistryEvent.Register<Feat> featRegistryEvent) {
        featRegistryEvent.getRegistry().registerAll(
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
        );
    }

    @SubscribeEvent
    public static void registryCreation(RegistryEvent.NewRegistry newRegistry) {
        new RegistryBuilder<Feat>()
                .setName(new ResourceLocation(ID, "feat"))
                .setType(Feat.class)
                .create();

        new RegistryBuilder<PathFeatureProvider>()
                .setName(new ResourceLocation(ID, "path_feature"))
                .setType(PathFeatureProvider.class)
                .create();
    }
}
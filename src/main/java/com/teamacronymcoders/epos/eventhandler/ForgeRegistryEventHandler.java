package com.teamacronymcoders.epos.eventhandler;

import com.google.common.collect.Lists;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatureProvider;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.api.source.SourceType;
import com.teamacronymcoders.epos.container.QuiverContainer;
import com.teamacronymcoders.epos.pathfeature.feat.FeatFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.skillxp.SkillXPFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.item.ItemRewardFeatureProvider;
import com.teamacronymcoders.epos.pathfeature.levelupskill.LevelUpSkillFeatureProvider;
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
                new LevelUpSkillFeatureProvider().setRegistryName(new ResourceLocation(ID, "level_up")),
                new ItemRewardFeatureProvider().setRegistryName(new ResourceLocation(ID, "item"))
        );
    }

    public static void registerRegistries(RegistryEvent.NewRegistry newRegistry) {
        new RegistryBuilder<SourceType>()
                .setName(new ResourceLocation(ID, "source_type"))
                .setType(SourceType.class)
                .create();

        new RegistryBuilder<PathFeatureProvider>()
                .setName(new ResourceLocation(ID, "path_feature_provider"))
                .setType(PathFeatureProvider.class)
                .create();
    }
}

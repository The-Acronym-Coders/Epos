package com.teamacronymcoders.epos.eventhandler;

import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatureProvider;
import com.teamacronymcoders.epos.api.source.SourceType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

import static com.teamacronymcoders.epos.api.EposAPI.ID;

public class ForgeRegistryEventHandler {
    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
//        event.getRegistry().registerAll(
//            IForgeContainerType.create(QuiverContainer::new).setRegistryName(new ResourceLocation(ID, "quiver_container"))
//        );
    }

    @SubscribeEvent
    public static void registerSound(RegistryEvent.Register<SoundEvent> eventRegistryEvent) {
//        eventRegistryEvent.getRegistry().registerAll(
//            EposSoundEvents.levelUp
//        );
    }


    @SubscribeEvent
    public static void registerClassFeatureProviders(RegistryEvent.Register<CharacterClassFeatureProvider> classFeatureProviderRegistryEvent) {
        classFeatureProviderRegistryEvent.getRegistry().registerAll(
//            new FeatFeatureProvider().setRegistryName(new ResourceLocation(ID, "feat")),
//            new SkillXPFeatureProvider().setRegistryName(new ResourceLocation(ID, "skill_xp")),
//            new LevelUpSkillFeatureProvider().setRegistryName(new ResourceLocation(ID, "level_up")),
//            new ItemRewardFeatureProvider().setRegistryName(new ResourceLocation(ID, "item"))
        );
    }

    public static void registerRegistries(RegistryEvent.NewRegistry newRegistry) {
        new RegistryBuilder<SourceType>()
            .setName(new ResourceLocation(ID, "source_type"))
            .setType(SourceType.class)
            .create();

        new RegistryBuilder<CharacterClassFeatureProvider>()
            .setName(new ResourceLocation(ID, "path_feature_provider"))
            .setType(CharacterClassFeatureProvider.class)
            .create();
    }
}

package com.teamacronymcoders.epos;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.EposResourceTypes;
import com.teamacronymcoders.epos.api.capability.CharacterStatsProvider;
import com.teamacronymcoders.epos.api.capability.NBTCapabilityStorage;
import com.teamacronymcoders.epos.api.characterstats.CharacterStats;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.json.JsonLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EposAPI.ID)
public class Epos {
    private static final String CONFIG = "epos.toml";
    //public static final AdvancedTitaniumTab EPOS_TAB = new AdvancedTitaniumTab("epos", false);
    private final JsonLoader<IPath> pathLoader = new JsonLoader<>("path", EposResourceTypes.PATH, IPath.class, EposAPI.PATH_REGISTRY);
    public static final ResourceLocation CHARACTER_STAT_CAPABILITY = new ResourceLocation(EposAPI.ID, "character_stats");

    public Epos() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addReloadListeners);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onAttachEntityCapabilities);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICharacterStats.class, NBTCapabilityStorage.create(), CharacterStats::new);
        DeferredWorkQueue.runLater(() -> {
            MinecraftForge.EVENT_BUS.post(new RegistrationEvent<>(ISkill.class, EposAPI.SKILL_REGISTRY));
            MinecraftForge.EVENT_BUS.post(new RegistrationEvent<>(IFeat.class, EposAPI.FEAT_REGISTRY));
        });
    }

    private void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(pathLoader);
    }

    private void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            CharacterStatsProvider provider = new CharacterStatsProvider();
            event.addCapability(CHARACTER_STAT_CAPABILITY, provider);
            event.addListener(provider::invalidate);
        }
    }
}

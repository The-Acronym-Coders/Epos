package com.teamacronymcoders.epos;

import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.capability.NBTCapabilityStorage;
import com.teamacronymcoders.epos.characterstats.CharacterStats;
import com.teamacronymcoders.epos.configs.EposClientConfig;
import com.teamacronymcoders.epos.feature.EposModules;
import com.teamacronymcoders.epos.json.JsonLoader;
import com.teamacronymcoders.epos.json.jsondirector.RegistryJsonDirector;
import com.teamacronymcoders.epos.json.jsonprovider.PathJsonProvider;
import com.teamacronymcoders.epos.json.jsonprovider.SkillJsonProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.teamacronymcoders.epos.api.EposAPI.*;

@Mod(ID)
@Mod.EventBusSubscriber
public class Epos extends ModuleController {
    public static final Logger LOGGER = LogManager.getLogger(ID);
    public static final AdvancedTitaniumTab EPOS_TAB = new AdvancedTitaniumTab("epos", false);

    public Epos() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStart);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, EposClientConfig.initialize());
    }

    @Override
    protected void initModules() {
        this.addModule(EposModules.QUIVER);
    }

    @SuppressWarnings("unused")
    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICharacterStats.class, NBTCapabilityStorage.create(), CharacterStats::new);
    }

    private void serverStart(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(new JsonLoader<>("epos/skills", LOGGER,
            new RegistryJsonDirector<>(SKILL_REGISTRY), new SkillJsonProvider()));
        event.getServer().getResourceManager().addReloadListener(new JsonLoader<>("epos/paths", LOGGER,
                new RegistryJsonDirector<>(PATH_REGISTRY), new PathJsonProvider()));

    }
}

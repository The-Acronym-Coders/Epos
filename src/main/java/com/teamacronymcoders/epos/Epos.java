package com.teamacronymcoders.epos;

import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.EposResourceType;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.registry.RegistrationEvent;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.characterstats.CharacterStats;
import com.teamacronymcoders.epos.gui.QuiverGui;
import com.teamacronymcoders.epos.container.QuiverContainer;
import com.teamacronymcoders.epos.feature.EposModules;
import com.teamacronymcoders.epos.json.JsonLoader;
import com.teamacronymcoders.epos.utils.configs.EMConfigs;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;

import static com.teamacronymcoders.epos.api.EposAPI.ID;
import static com.teamacronymcoders.epos.api.EposAPI.PATH_REGISTRY;

@Mod(ID)
@Mod.EventBusSubscriber
public class Epos extends ModuleController {
    public static final Logger LOGGER = LogManager.getLogger(ID);
    public static final AdvancedTitaniumTab EPOS_TAB = new AdvancedTitaniumTab("epos", false);
    private static final String config = "epos.toml";
    private final JsonLoader<IPath> pathLoader = new JsonLoader<>("path", EposResourceType.PATH, IPath.class, PATH_REGISTRY);

    public Epos() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverStart);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EMConfigs.build, new File(FMLPaths.CONFIGDIR.get().toFile(), config).getAbsolutePath());
    }

    @Override
    protected void initModules() {
        this.addModule(EposModules.ITEMS);
    }

    @SuppressWarnings("unused")
    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICharacterStats.class, new Capability.IStorage<ICharacterStats>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<ICharacterStats> capability, ICharacterStats instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<ICharacterStats> capability, ICharacterStats instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, CharacterStats::new);

        DeferredWorkQueue.runLater(() -> {
            MinecraftForge.EVENT_BUS.post(new RegistrationEvent<>(ISkill.class, EposAPI.SKILL_REGISTRY));
            MinecraftForge.EVENT_BUS.post(new RegistrationEvent<>(IFeat.class, EposAPI.FEAT_REGISTRY));
        });
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(QuiverContainer.TYPE, QuiverGui::new);
    }

    private void serverStart(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(pathLoader);
    }
}

package com.teamacronymcoders.epos;

import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.capability.NBTCapabilityStorage;
import com.teamacronymcoders.epos.characterstats.CharacterStats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.teamacronymcoders.epos.api.EposAPI.*;

@Mod(ID)
public class Epos extends ModuleController {
    public static final Logger LOGGER = LogManager.getLogger(ID);
    public static final AdvancedTitaniumTab EPOS_TAB = new AdvancedTitaniumTab("epos", false);

    public Epos() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::serverStart);
    }

    @Override
    protected void initModules() {}

    @SuppressWarnings("unused")
    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICharacterStats.class, NBTCapabilityStorage.create(), CharacterStats::new);
    }

    private void serverStart(FMLServerAboutToStartEvent event) {

    }
}

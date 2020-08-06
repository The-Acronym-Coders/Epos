package com.teamacronymcoders.epos;

import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.capability.CharacterStatsProvider;
import com.teamacronymcoders.epos.api.characterstats.CharacterStats;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.capability.NBTCapabilityStorage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EposAPI.ID)
public class Epos {
    public static final AdvancedTitaniumTab EPOS_TAB = new AdvancedTitaniumTab("epos", false);
    public static final ResourceLocation CHARACTER_STAT_CAPABILITY = new ResourceLocation(EposAPI.ID, "character_stats");

    public Epos() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onAttachEntityCapabilities);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICharacterStats.class, NBTCapabilityStorage.create(), CharacterStats::new);
    }

    private void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            CharacterStatsProvider provider = new CharacterStatsProvider((LivingEntity) event.getObject());
            event.addCapability(CHARACTER_STAT_CAPABILITY, provider);
            event.addListener(provider::invalidate);
        }
    }
}

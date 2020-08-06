package com.teamacronymcoders.epos;

import com.teamacronymcoders.epos.advancement.EposAdvancementProvider;
import com.teamacronymcoders.epos.advancement.EposRootAdvancementsProvider;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.lang.EposLangProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = EposAPI.ID, bus = Bus.MOD)
public class EposDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeClient()) {
            //Client side data generators
            EposLangProvider.registerLangProviders(generator);
        }
        if (event.includeServer()) {
            //Server side data generators
            EposAdvancementProvider.registerAdvancementProvider(generator);
        }
    }
}

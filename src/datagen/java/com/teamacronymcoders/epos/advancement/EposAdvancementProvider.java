package com.teamacronymcoders.epos.advancement;

import net.minecraft.data.DataGenerator;

public class EposAdvancementProvider {
    public static void registerAdvancementProvider(DataGenerator generator) {
        generator.addProvider(new EposRootAdvancementsProvider(generator));
    }
}

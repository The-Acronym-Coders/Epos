package com.teamacronymcoders.epos.lang;

import net.minecraft.data.DataGenerator;

public class EposLangProvider {

    public static void registerLangProviders(DataGenerator generator) {
        generator.addProvider(new EposEnglishLangProvider(generator));
        generator.addProvider(new EposSwedishLangProvider(generator));
    }
}

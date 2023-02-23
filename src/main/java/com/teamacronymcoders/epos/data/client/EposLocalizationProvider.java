/*
 * Copyright (c) Team Acronym Coders
 * SPDX-License-Identifier: MIT
 */

package com.teamacronymcoders.epos.data.client;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.util.EposLocalizationKeys;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A {@link DataProvider} used to generate localizations for any locales
 * specified.
 */
public class EposLocalizationProvider implements DataProvider {

    private final Function<String, LanguageProvider> factory;
    private final Map<String, LanguageProvider> localizations;

    /**
     * Constructs a new provider to generate language files for many locales.
     *
     * @param output metadata related to the output location of the generator
     */
    public EposLocalizationProvider(PackOutput output) {
        this.factory = locale -> new LanguageProvider(output, Epos.ID, locale) {
            @Override
            protected void addTranslations() {}
        };
        this.localizations = new HashMap<>();
    }

    /**
     * Adds the localizations for the mod.
     */
    private void addLocalizations() {
        this.localizeAll(EposLocalizationKeys.PACK_DESCRIPTION, Map.of(
                EposLocalizationKeys.EN_US, "Epos Resources"
        ));
    }

    /**
     * Localizes all names for a given key.
     *
     * @param key the key to add localizations for
     * @param localeToName a map of locales to localized names
     */
    private void localizeAll(String key, Map<String, String> localeToName) {
        this.localizeAll((provider, name) -> provider.add(key, name), localeToName);
    }

    /**
     * Localizes all names for a given provider method.
     *
     * @param nameInserter a consumer containing the {@link LanguageProvider} and
     *                     the localized name for that provider
     * @param localeToName a map of locales to localized names
     */
    private void localizeAll(BiConsumer<LanguageProvider, String> nameInserter, Map<String, String> localeToName) {
        localeToName.forEach((locale, name) -> this.forLocale(locale, provider -> nameInserter.accept(provider, name)));
    }

    /**
     * Grants access to the {@link LanguageProvider} for the specific locale to
     * add keys.
     *
     * @param locale the locale to get the localization for
     * @param provider a consumer containing the {@link LanguageProvider}
     */
    private void forLocale(String locale, Consumer<LanguageProvider> provider) {
        provider.accept(this.localizations.computeIfAbsent(locale, this.factory));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        this.addLocalizations();

        CompletableFuture<?>[] futures = new CompletableFuture<?>[this.localizations.size()];
        int index = 0;
        for (var provider : this.localizations.values()) futures[index++] = provider.run(pOutput);
        return CompletableFuture.allOf(futures);
    }

    @Override
    public String getName() {
        return "Epos Localizations";
    }
}

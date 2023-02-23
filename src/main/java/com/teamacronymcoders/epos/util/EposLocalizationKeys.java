/*
 * Copyright (c) Team Acronym Coders
 * SPDX-License-Identifier: MIT
 */

package com.teamacronymcoders.epos.util;

import com.teamacronymcoders.epos.Epos;

/**
 * A utility containing information related to the localization keys used within
 * this mod.
 */
public class EposLocalizationKeys {

    /**
     * The English (United States) locale code.
     */
    public static final String EN_US = "en_us";

    /**
     * A translation key representing the description of this mod's resources
     * in the {@code pack.mcmeta}.
     */
    public static final String PACK_DESCRIPTION = create("pack", "description");

    /**
     * Creates a new localization key with this mod's id and interns it for
     * any future use.
     *
     * @param type the object type the localization is representing
     * @param key the key of the object the localization is representing
     * @return a localization key
     */
    public static String create(String type, String key) {
        return (type + "." + Epos.ID + "." + key).intern();
    }
}

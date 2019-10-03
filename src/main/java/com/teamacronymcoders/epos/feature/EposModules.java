package com.teamacronymcoders.epos.feature;

import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.feature.quiver.QuiverItem;
import net.minecraft.item.Item;

public class EposModules {
    public static final Module.Builder QUIVER =
            Module.builder("quiver")
                    .force()
                    .description("Adds The Quiver").feature(
                    Feature.builder("quiver")
                            .content(Item.class, new QuiverItem().setRegistryName(EposAPI.ID, "quiver"))
            );
}

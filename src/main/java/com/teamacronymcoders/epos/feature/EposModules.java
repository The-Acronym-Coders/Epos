package com.teamacronymcoders.epos.feature;

import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.feature.quiver.QuiverItem;
import net.minecraft.item.Item;

public class EposModules {
    public static final Module.Builder ITEMS =
            Module.builder("items")
                    .force()
                    .description("Adds Epos Items")
                    .feature(Feature.builder("Quiver")
                            .content(Item.class, new QuiverItem().setRegistryName(EposAPI.ID, "quiver"))
                    );


}

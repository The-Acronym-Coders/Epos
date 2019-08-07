package com.teamacronymcoders.mcrpg.api.feat;

import com.google.common.collect.Lists;
import com.teamacronymcoders.mcrpg.api.registry.MissingRegistryEntry;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class MissingFeat extends MissingRegistryEntry<IFeat> implements IFeat {
    public MissingFeat(String registryName) {
        super(new ResourceLocation(registryName), "feat");
    }

    @Override
    public List<FeatEventHandler<?>> getEventHandlers() {
        return Lists.newArrayList();
    }
}

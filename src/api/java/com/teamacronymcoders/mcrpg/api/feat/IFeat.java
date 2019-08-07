package com.teamacronymcoders.mcrpg.api.feat;

import com.teamacronymcoders.mcrpg.api.registry.INamedRegistryEntry;

import java.util.List;

public interface IFeat extends INamedRegistryEntry<IFeat> {
    List<FeatEventHandler<?>> getEventHandlers();
}

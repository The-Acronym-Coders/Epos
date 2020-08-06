package com.teamacronymcoders.epos.api.feat;

import com.teamacronymcoders.epos.api.registry.INamedRegistryEntry;

import java.util.List;

public interface IFeat extends INamedRegistryEntry<IFeat> {
    List<FeatEventHandler<?>> getEventHandlers();
}

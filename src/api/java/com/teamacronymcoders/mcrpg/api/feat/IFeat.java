package com.teamacronymcoders.eposmajorum.api.feat;

import com.teamacronymcoders.eposmajorum.api.registry.INamedRegistryEntry;

import java.util.List;

public interface IFeat extends INamedRegistryEntry<IFeat> {
    List<FeatEventHandler<?>> getEventHandlers();
}

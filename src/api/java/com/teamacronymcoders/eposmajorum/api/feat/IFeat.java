package com.teamacronymcoders.eposmajorum.api.feat;

import com.teamacronymcoders.eposmajorum.api.registry.IRegistryEntry;

import java.util.List;

public interface IFeat extends IRegistryEntry {
    List<FeatEventHandler> getEventHandlers();
}

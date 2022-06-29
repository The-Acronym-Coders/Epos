package com.teamacronymcoders.epos.api.charactersheet.storage;

import com.teamacronymcoders.epos.api.charactersheet.storage.info.PathInfo;
import net.minecraft.resources.ResourceLocation;

public interface IPathStorage {

  PathInfo getPath(ResourceLocation pathId);

  void levelUpPath(ResourceLocation pathId, int levels);

  void levelDownPath(ResourceLocation pathId, int levels);
}

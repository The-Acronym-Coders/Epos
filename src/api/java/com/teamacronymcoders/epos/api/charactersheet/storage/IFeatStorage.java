package com.teamacronymcoders.epos.api.charactersheet.storage;

import com.teamacronymcoders.epos.api.charactersheet.storage.info.FeatInfo;
import net.minecraft.resources.ResourceLocation;

public interface IFeatStorage {

  FeatInfo getSkill(ResourceLocation featId);

}

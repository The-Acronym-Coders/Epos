package com.teamacronymcoders.epos.api.charactersheet.storage;

import com.teamacronymcoders.epos.api.charactersheet.storage.info.SkillInfo;
import net.minecraft.resources.ResourceLocation;

public interface ISkillStorage {

  SkillInfo getSkill(ResourceLocation skillId);

}

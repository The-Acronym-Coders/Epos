package com.teamacronymcoders.epos.charactersheet.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.ISkillStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.SkillInfo;
import com.teamacronymcoders.epos.charactersheet.storage.info.ImplExtendableSkillInfo;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record SkillStorage(Map<ResourceLocation, ImplExtendableSkillInfo> skills) implements ISkillStorage {

  public static final Codec<SkillStorage> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(Codec.unboundedMap(ResourceLocation.CODEC, ImplExtendableSkillInfo.CODEC).fieldOf("skills").forGetter(SkillStorage::skills))
          .apply(instance, SkillStorage::new));

  @Override
  public SkillInfo getSkill(ResourceLocation skillId) {
    return this.getOrCreate(skillId);
  }

  private SkillInfo getOrCreate(ResourceLocation skillId) {
    // Todo: Rewrite to support max level of Skills
    return this.skills.computeIfAbsent(skillId, (rl) -> new ImplExtendableSkillInfo(skillId, 0, 0, false));
  }

}

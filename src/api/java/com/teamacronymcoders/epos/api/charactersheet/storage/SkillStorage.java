package com.teamacronymcoders.epos.api.charactersheet.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.SkillInfo;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record SkillStorage(Map<ResourceLocation, SkillInfo> skills) {

  public static final Codec<SkillStorage> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(Codec.unboundedMap(ResourceLocation.CODEC, SkillInfo.CODEC).fieldOf("skills").forGetter(SkillStorage::skills))
          .apply(instance, SkillStorage::new));


  public SkillInfo getSkill(ResourceLocation skillId) {
    return this.getOrCreate(skillId);
  }

  private SkillInfo getOrCreate(ResourceLocation skillId) {
    // Todo: Rewrite to support max level of Skills
    return this.skills.computeIfAbsent(skillId, (rl) -> new SkillInfo(skillId, 0, 0, false));
  }

  public void levelUpSkill(ResourceLocation skillId, int levels) {
    SkillInfo info = this.getSkill(skillId);
    // Todo: Figure out utility methods for calculating the new experience values
    // Todo: Rewrite to support max level of Skills
    this.skills.put(skillId, new SkillInfo(info.skill(), Math.min(info.level() + levels, 10), info.experience(), info.isActive()));
  }

  public void levelDownSkill(ResourceLocation skillId, int levels) {
    SkillInfo info = this.getSkill(skillId);
    // Todo: Figure out utility methods for calculating the new experience values
    // Todo: Rewrite to support max level of Skills
    this.skills.put(skillId, new SkillInfo(info.skill(), Math.max(info.level() - levels, 0), info.experience(), info.isActive()));
  }

  public void addExperience(ResourceLocation skillId, int exp) {
    SkillInfo info = this.getSkill(skillId);
    // Todo: Figure out utility methods for calculating the new level values
    // Todo: Rewrite to support max experience of Skills
    this.skills.put(skillId, new SkillInfo(info.skill(), info.level(), Math.min(info.experience() + exp, Integer.MAX_VALUE - 1), info.isActive()));
  }

  public void removeExperience(ResourceLocation skillId, int exp) {
    SkillInfo info = this.getSkill(skillId);
    // Todo: Figure out utility methods for calculating the new level values
    // Todo: Rewrite to support max experience of Skills
    this.skills.put(skillId, new SkillInfo(info.skill(), info.level(), Math.max(info.experience() - exp, Integer.MAX_VALUE - 1), info.isActive()));
  }

  public boolean isActive(ResourceLocation skillId) {
    SkillInfo info = this.getSkill(skillId);
    return info.isActive();
  }

  public void setActive(ResourceLocation skillId, boolean isActive) {
    SkillInfo info = this.getSkill(skillId);
    this.skills.put(skillId, new SkillInfo(info.skill(), info.level(), info.experience(), isActive));
  }
}

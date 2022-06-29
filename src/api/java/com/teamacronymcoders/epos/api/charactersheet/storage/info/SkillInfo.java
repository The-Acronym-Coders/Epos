package com.teamacronymcoders.epos.api.charactersheet.storage.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record SkillInfo(ResourceLocation skill, int level, int experience, boolean isActive) {
  public static final Codec<SkillInfo> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(ResourceLocation.CODEC.fieldOf("skill").forGetter(SkillInfo::skill),
                  Codec.INT.fieldOf("level").forGetter(SkillInfo::level),
                  Codec.INT.fieldOf("experience").forGetter(SkillInfo::experience),
                  Codec.BOOL.fieldOf("isActive").forGetter(SkillInfo::isActive))
          .apply(instance, SkillInfo::new));
}

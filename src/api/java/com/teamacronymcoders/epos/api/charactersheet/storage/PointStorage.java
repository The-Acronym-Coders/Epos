package com.teamacronymcoders.epos.api.charactersheet.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PointStorage(int pathPoints, int skillPoints, int featPoints) {
  public static final Codec<PointStorage> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(
                  Codec.INT.fieldOf("pathPoints").forGetter(PointStorage::getPathPoints),
                  Codec.INT.fieldOf("skillPoints").forGetter(PointStorage::getSkillPoints),
                  Codec.INT.fieldOf("featPoints").forGetter(PointStorage::getFeatPoints)
          ).apply(instance, PointStorage::new));

  public int getPathPoints() {
    return this.pathPoints;
  }

  public int getSkillPoints() {
    return this.skillPoints;
  }

  public int getFeatPoints() {
    return this.featPoints;
  }
}

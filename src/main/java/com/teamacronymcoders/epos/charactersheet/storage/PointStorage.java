package com.teamacronymcoders.epos.charactersheet.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.IPointStorage;

public record PointStorage(int pathPoints, int skillPoints, int featPoints) implements IPointStorage {
  public static final Codec<PointStorage> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(
                  Codec.INT.fieldOf("pathPoints").forGetter(PointStorage::getPathPoints),
                  Codec.INT.fieldOf("skillPoints").forGetter(PointStorage::getSkillPoints),
                  Codec.INT.fieldOf("featPoints").forGetter(PointStorage::getFeatPoints)
          ).apply(instance, PointStorage::new));


  @Override
  public int getPathPoints() {
    return this.pathPoints;
  }

  @Override
  public int getSkillPoints() {
    return this.skillPoints;
  }

  @Override
  public int getFeatPoints() {
    return this.featPoints;
  }
}

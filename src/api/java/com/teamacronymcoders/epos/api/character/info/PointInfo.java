package com.teamacronymcoders.epos.api.character.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PointInfo {

    public static final Codec<PointInfo> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Codec.INT.optionalFieldOf("pathPoints", 0).forGetter(PointInfo::getPathPoints),
            Codec.INT.optionalFieldOf("skillPoints", 0).forGetter(PointInfo::getSkillPoints),
            Codec.INT.optionalFieldOf("featPoints", 0).forGetter(PointInfo::getFeatPoints)
        ).apply(instance, PointInfo::new)
    );

    private int pathPoints;
    private int skillPoints;
    private int featPoints;

    public PointInfo() {
        this.pathPoints = 0;
        this.skillPoints = 0;
        this.featPoints = 0;
    }

    public PointInfo(int pathPoints, int skillPoints, int featPoints) {
        this.pathPoints = pathPoints;
        this.skillPoints = skillPoints;
        this.featPoints = featPoints;
    }

    public int getPathPoints() {
        return pathPoints;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getFeatPoints() {
        return featPoints;
    }
}

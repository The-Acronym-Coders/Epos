package com.teamacronymcoders.epos.api.character.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PointInfo {

    public static final Codec<PointInfo> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Codec.INT.optionalFieldOf("pathPoints", 1).forGetter(PointInfo::getPathPoints),
            Codec.INT.optionalFieldOf("skillPoints", 1).forGetter(PointInfo::getSkillPoints),
            Codec.INT.optionalFieldOf("featPoints", 1).forGetter(PointInfo::getFeatPoints)
        ).apply(instance, PointInfo::new)
    );

    private int pathPoints;
    private int skillPoints;
    private int featPoints;

    public PointInfo() {
        this.pathPoints = 1;
        this.skillPoints = 1;
        this.featPoints = 1;
    }

    public PointInfo(int pathPoints, int skillPoints, int featPoints) {
        this.pathPoints = pathPoints;
        this.skillPoints = skillPoints;
        this.featPoints = featPoints;
    }

    // Path
    public void addPathPoints(int points) {
        this.pathPoints = Math.min(Integer.MAX_VALUE, this.pathPoints + points);
    }

    public void removePathPoints(int points) {
        this.pathPoints = Math.max(0, this.pathPoints - points);
    }

    public int getPathPoints() {
        return pathPoints;
    }

    // Skill
    public void addSkillPoints(int points) {
        this.skillPoints = Math.min(Integer.MAX_VALUE, this.skillPoints + points);
    }

    public void removeSkillPoints(int points) {
        this.skillPoints = Math.max(0, this.skillPoints - points);
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    // Feat
    public void addFeatPoints(int points) {
        this.featPoints = Math.min(Integer.MAX_VALUE, this.featPoints + points);
    }

    public void removeFeatPoints(int points) {
        this.featPoints = Math.max(0, this.featPoints - points);
    }

    public int getFeatPoints() {
        return featPoints;
    }
}

package com.teamacronymcoders.epos.api.character.info;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CharacterInfo {

    public static final Codec<CharacterInfo> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.INT.optionalFieldOf("currentLevel", 1).forGetter(CharacterInfo::getCurrentLevel),
                    Codec.INT.optionalFieldOf("experience", 0).forGetter(CharacterInfo::getExperience),
                    PointInfo.CODEC.optionalFieldOf("pointInfo", new PointInfo()).forGetter(CharacterInfo::getPointInfo)
            ).apply(instance, CharacterInfo::new)
    );

    public int currentLevel;
    public int experience;

    private PointInfo pointInfo;

    public CharacterInfo() {
        this.currentLevel = 1;
        this.experience = 0;
        this.pointInfo = new PointInfo();
    }

    public CharacterInfo(int currentLevel, int experience, PointInfo pointInfo) {
        this.currentLevel = currentLevel;
        this.experience = experience;
        this.pointInfo = pointInfo;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getExperience() {
        return this.experience;
    }

    public PointInfo getPointInfo() {
        return this.pointInfo;
    }

}

package com.teamacronymcoders.epos.api.characterstats.points;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.EposConstants.NBT;
import net.minecraft.util.IStringSerializable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PointType implements IStringSerializable {
    PATH("path", NBT.PATH_POINTS_CURRENT, NBT.PATH_POINTS_TOTAL),
    SKILL("skill", NBT.SKILL_POINTS_CURRENT, NBT.SKILL_POINTS_TOTAL),
    FEAT("feat", NBT.FEAT_POINTS_CURRENT, NBT.FEAT_POINTS_TOTAL);

    public static final Codec<PointType> POINT_TYPE_CODEC = IStringSerializable.createEnumCodec(PointType::values, PointType::getPointType);
    private static final Map<String, PointType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(PointType::getString, pointType -> pointType));

    private final String name;
    private final String currentNBT;
    private final String totalNBT;

    PointType(String name, String currentNBT, String totalNBT) {
        this.name = name;
        this.currentNBT = currentNBT;
        this.totalNBT = totalNBT;
    }

    public String getCurrentNBT() {
        return currentNBT;
    }

    public String getTotalNBT() {
        return totalNBT;
    }

    public static PointType getPointType(String name) {
        return BY_NAME.get(name);
    }

    @Override
    public String getString() {
        return name;
    }
}

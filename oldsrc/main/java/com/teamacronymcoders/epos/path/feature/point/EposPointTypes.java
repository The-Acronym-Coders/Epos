package com.teamacronymcoders.epos.path.feature.point;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public enum EposPointTypes implements StringRepresentable {
    PATH("path"), SKILL("skill"), FEAT("feat");

    public static final Codec<EposPointTypes> CODEC = StringRepresentable.fromEnum(EposPointTypes::values, EposPointTypes::byName);
    private static final Map<String, EposPointTypes> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(EposPointTypes::getSerializedName, grantType -> grantType));

    final String name;

    EposPointTypes(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    @Nullable
    public static EposPointTypes byName(String name) {
        return BY_NAME.get(name.toLowerCase(Locale.ROOT));
    }
}

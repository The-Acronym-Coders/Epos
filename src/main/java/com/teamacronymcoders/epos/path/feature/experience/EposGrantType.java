package com.teamacronymcoders.epos.path.feature.experience;

import com.mojang.serialization.Codec;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public enum EposGrantType implements IStringSerializable {
    CHARACTER("character"), SKILL("skill");

    public static final Codec<EposGrantType> CODEC = IStringSerializable.fromEnum(EposGrantType::values, EposGrantType::byName);
    private static final Map<String, EposGrantType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(EposGrantType::getSerializedName, grantType -> grantType));

    final String name;

    EposGrantType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    @Nullable
    public static EposGrantType byName(String name) {
        return BY_NAME.get(name.toLowerCase(Locale.ROOT));
    }
}

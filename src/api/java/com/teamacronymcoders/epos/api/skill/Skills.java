package com.teamacronymcoders.epos.api.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Skills {

    public static final Codec<Skills> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Codec.unboundedMap(ResourceLocation.CODEC, SkillInfo.CODEC).fieldOf("skills").forGetter(Skills::getSkillInfoMap))
            .apply(instance, Skills::new));

    private final Map<ResourceLocation, SkillInfo> skillInfoMap;

    /**
     * Default Constructor
     */
    public Skills() {
        this.skillInfoMap = new HashMap<>();
    }

    /**
     * Codec Constructor
     *
     * @param skillInfoMap Map of the currently stored Skill(s) on the Character.
     */
    public Skills(Map<ResourceLocation, SkillInfo> skillInfoMap) {
        this.skillInfoMap = skillInfoMap;
    }

    @Nonnull
    public SkillInfo getOrCreate(ResourceLocation id) {
        return skillInfoMap.computeIfAbsent(id, SkillInfo::new);
    }

    public Map<ResourceLocation, SkillInfo> getSkillInfoMap() {
        return skillInfoMap;
    }
}

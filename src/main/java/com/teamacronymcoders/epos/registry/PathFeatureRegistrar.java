package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.path.features.PathFeatureSerializer;
import com.teamacronymcoders.epos.path.feature.experience.EposGrantType;
import com.teamacronymcoders.epos.path.feature.experience.ExperiencePathFeature;
import com.teamacronymcoders.epos.path.feature.experience.LevelPathFeature;
import com.teamacronymcoders.epos.path.feature.grant.GrantPathFeature;
import com.teamacronymcoders.epos.path.feature.point.EposPointTypes;
import com.teamacronymcoders.epos.path.feature.point.PointPathFeature;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.teamacronymcoders.epos.util.EposRegistries;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ResourceLocation;

public class PathFeatureRegistrar {

    public static void register() {}

    public static final RegistryEntry<PathFeatureSerializer> EXPERIENCE_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("experience_feature_serializer", () -> {
                Codec<ExperiencePathFeature> codec = RecordCodecBuilder.create(instance -> instance
                        .group(
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(ExperiencePathFeature::getName),
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(ExperiencePathFeature::getDescription),
                                EposGrantType.CODEC.fieldOf("type").forGetter(ExperiencePathFeature::getGrantType),
                                ResourceLocation.CODEC.optionalFieldOf("skillID", EposRegistries.MISSING_ENTRY).forGetter(ExperiencePathFeature::getSkillID),
                                Codec.INT.fieldOf("experience").forGetter(ExperiencePathFeature::getExperience))
                        .apply(instance, ExperiencePathFeature::new));
                return codec;
            }).register();

    public static final RegistryEntry<PathFeatureSerializer> LEVEL_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("level_feature_serializer", () -> {
                Codec<LevelPathFeature> codec = RecordCodecBuilder.create(instance -> instance
                        .group(
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(LevelPathFeature::getName),
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(LevelPathFeature::getDescription),
                                EposGrantType.CODEC.fieldOf("type").forGetter(LevelPathFeature::getGrantType),
                                ResourceLocation.CODEC.optionalFieldOf("skillID", EposRegistries.MISSING_ENTRY).forGetter(LevelPathFeature::getSkillID),
                                Codec.INT.fieldOf("levels").forGetter(LevelPathFeature::getLevels))
                        .apply(instance, LevelPathFeature::new));
                return codec;
            }).register();

    public static final RegistryEntry<PathFeatureSerializer> GRANT_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("grant_feature_serializer", () -> {
                Codec<GrantPathFeature> codec = RecordCodecBuilder.create(instance -> instance
                        .group(
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(GrantPathFeature::getName),
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(GrantPathFeature::getDescription),
                                com.teamacronymcoders.epos.api.enums.EposGrantType.CODEC.fieldOf("type").forGetter(GrantPathFeature::getType),
                                ResourceLocation.CODEC.fieldOf("id").forGetter(GrantPathFeature::getId))
                        .apply(instance, GrantPathFeature::new));
                return codec;
            }).register();

    public static final RegistryEntry<PathFeatureSerializer> POINT_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("point_feature_serializer", () -> {
                Codec<PointPathFeature> codec = RecordCodecBuilder.create(instance -> instance
                        .group(
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(PointPathFeature::getName),
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(PointPathFeature::getDescription),
                                EposPointTypes.CODEC.fieldOf("type").forGetter(PointPathFeature::getType),
                                Codec.INT.fieldOf("amount").forGetter(PointPathFeature::getAmount))
                        .apply(instance, PointPathFeature::new));
                return codec;
            }).register();

}

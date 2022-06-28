package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
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

public class PathFeatureRegistrar {

    public static void register() {}

    public static final RegistryEntry<Codec<? extends IPathFeature>> EXPERIENCE_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("experience_feature_serializer", () -> RecordCodecBuilder.<ExperiencePathFeature>create(instance -> instance
                    .group(
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(ExperiencePathFeature::getName),
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(ExperiencePathFeature::getDescription),
                            EposGrantType.CODEC.fieldOf("type").forGetter(ExperiencePathFeature::getGrantType),
                            ResourceLocation.CODEC.optionalFieldOf("skillID", EposRegistries.MISSING_ENTRY).forGetter(ExperiencePathFeature::getSkillID),
                            Codec.INT.fieldOf("experience").forGetter(ExperiencePathFeature::getExperience))
                    .apply(instance, ExperiencePathFeature::new)));

    public static final RegistryEntry<Codec<? extends IPathFeature>> LEVEL_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("level_feature_serializer", () -> RecordCodecBuilder.<LevelPathFeature>create(instance -> instance
                    .group(
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(LevelPathFeature::getName),
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(LevelPathFeature::getDescription),
                            EposGrantType.CODEC.fieldOf("type").forGetter(LevelPathFeature::getGrantType),
                            ResourceLocation.CODEC.optionalFieldOf("skillID", EposRegistries.MISSING_ENTRY).forGetter(LevelPathFeature::getSkillID),
                            Codec.INT.fieldOf("levels").forGetter(LevelPathFeature::getLevels))
                    .apply(instance, LevelPathFeature::new)));

    public static final RegistryEntry<Codec<? extends IPathFeature>> GRANT_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("grant_feature_serializer", () -> RecordCodecBuilder.<GrantPathFeature>create(instance -> instance
                    .group(
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(GrantPathFeature::getName),
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(GrantPathFeature::getDescription),
                            com.teamacronymcoders.epos.api.enums.EposGrantType.CODEC.fieldOf("type").forGetter(GrantPathFeature::getType),
                            ResourceLocation.CODEC.fieldOf("id").forGetter(GrantPathFeature::getId))
                    .apply(instance, GrantPathFeature::new)));

    public static final RegistryEntry<Codec<? extends IPathFeature>> POINT_FEATURE_SERIALIZER = Epos.instance().getRegistrate()
            .pathFeatureSerializer("point_feature_serializer", () -> RecordCodecBuilder.<PointPathFeature>create(instance -> instance
                    .group(
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(PointPathFeature::getName),
                            EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(PointPathFeature::getDescription),
                            EposPointTypes.CODEC.fieldOf("type").forGetter(PointPathFeature::getType),
                            Codec.INT.fieldOf("amount").forGetter(PointPathFeature::getAmount))
                    .apply(instance, PointPathFeature::new)));

}

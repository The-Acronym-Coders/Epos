package com.teamacronymcoders.epos.impl;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.builder.PathBuilder;
import com.teamacronymcoders.epos.api.enums.EposGrantType;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.path.feature.experience.ExperiencePathFeature;
import com.teamacronymcoders.epos.path.feature.experience.LevelPathFeature;
import com.teamacronymcoders.epos.path.feature.grant.GrantPathFeature;
import com.teamacronymcoders.epos.path.feature.point.EposPointTypes;
import com.teamacronymcoders.epos.path.feature.point.PointPathFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class EposPaths {
    private static final IPath test = PathBuilder.create(new ResourceLocation(Epos.ID, "test"))
            .name(() -> Component.literal("test"))
            .description(() -> Component.literal("testDescription"))
            .maxLevel(() -> 1)
            .withPathFeatureForLevel(
                    1, new ExperiencePathFeature(
                            Component.literal("test"),
                            Component.literal("testDesc"),
                                com.teamacronymcoders.epos.path.feature.experience.EposGrantType.SKILL,
                                new ResourceLocation(Epos.ID, "test"),
                                5
                    )
            )
            .withPathFeatureForLevel(
                    2, new LevelPathFeature(
                            Component.literal("test"),
                            Component.literal("testDesc"),
                                com.teamacronymcoders.epos.path.feature.experience.EposGrantType.CHARACTER,
                                null,
                                1
                    )
            )
            .withPathFeatureForLevel(
                    3, new GrantPathFeature(
                            Component.literal("test"),
                            Component.literal("testDesc"),
                                EposGrantType.FEAT,
                                new ResourceLocation(Epos.ID, "test")
                    )
            )
            .withPathFeatureForLevel(
                    4, new PointPathFeature(
                            Component.literal("test"),
                            Component.literal("testDesc"),
                                EposPointTypes.PATH,
                                5
                    )
            )
            .createEntry();
}

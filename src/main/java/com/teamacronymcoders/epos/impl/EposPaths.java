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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class EposPaths {
    private static final IPath test = PathBuilder.create(new ResourceLocation(Epos.ID, "test"))
            .name(() -> new StringTextComponent("test"))
            .description(() -> new StringTextComponent("testDescription"))
            .maxLevel(() -> 1)
            .withPathFeatureForLevel(
                    1, new ExperiencePathFeature(
                                new StringTextComponent("test"),
                                new StringTextComponent("testDesc"),
                                com.teamacronymcoders.epos.path.feature.experience.EposGrantType.SKILL,
                                new ResourceLocation(Epos.ID, "test"),
                                5
                    )
            )
            .withPathFeatureForLevel(
                    2, new LevelPathFeature(
                                new StringTextComponent("test"),
                                new StringTextComponent("testDesc"),
                                com.teamacronymcoders.epos.path.feature.experience.EposGrantType.CHARACTER,
                                null,
                                1
                    )
            )
            .withPathFeatureForLevel(
                    3, new GrantPathFeature(
                                new StringTextComponent("test"),
                                new StringTextComponent("testDesc"),
                                EposGrantType.FEAT,
                                new ResourceLocation(Epos.ID, "test")
                    )
            )
            .withPathFeatureForLevel(
                    4, new PointPathFeature(
                                new StringTextComponent("test"),
                                new StringTextComponent("testDesc"),
                                EposPointTypes.PATH,
                                5
                    )
            )
            .createEntry();
}

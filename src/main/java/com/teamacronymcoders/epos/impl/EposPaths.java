package com.teamacronymcoders.epos.impl;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.builder.PathBuilder;
import com.teamacronymcoders.epos.api.enums.EposGrantType;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.path.feature.grant.GrantPathFeature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class EposPaths {
    private static final IPath test = PathBuilder.create(new ResourceLocation(Epos.ID, "test"))
            .name(() -> new StringTextComponent("test"))
            .description(() -> new StringTextComponent("testDescription"))
            .maxLevel(() -> 1)
            .withPathFeatureForLevel(1, new GrantPathFeature(new StringTextComponent("test"), new StringTextComponent("testDesc"), EposGrantType.FEAT, new ResourceLocation(Epos.ID, "test")))
            .createEntry();
}

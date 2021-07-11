package com.teamacronymcoders.epos.impl;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.enums.EposGrantType;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.path.feature.grant.GrantPathFeature;
import com.teamacronymcoders.epos.registry.builder.PathBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class EposPaths {
    private static final IPath test = PathBuilder.create(Epos.instance().getRegistrate(), Epos.instance().getRegistrate(), "test", null)
            .name(() -> new StringTextComponent("test"))
            .description(() -> new StringTextComponent("testDescription"))
            .maxLevel(() -> 1)
            //.withPathFeatureForLevel(1, new GrantPathFeature(new StringTextComponent("test"), new StringTextComponent("testDesc"), EposGrantType.FEAT, new ResourceLocation(Epos.ID, "test")))
            .createEntry();
}

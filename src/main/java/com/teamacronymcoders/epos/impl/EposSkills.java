package com.teamacronymcoders.epos.impl;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.builder.SkillBuilder;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class EposSkills {
    private static final ISkill test = SkillBuilder.create(new ResourceLocation(Epos.ID, "test"))
            .name(() -> new TextComponent("test"))
            .description(() -> new TextComponent("testDescription"))
            .maxLevel(() -> 1)
            .createEntry();
}

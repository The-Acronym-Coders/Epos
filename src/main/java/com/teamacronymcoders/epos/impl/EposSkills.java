package com.teamacronymcoders.epos.impl;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.registry.builder.SkillBuilder;
import net.minecraft.util.text.StringTextComponent;

public class EposSkills {
    private static final ISkill test = SkillBuilder.create(Epos.instance().getRegistrate(), Epos.instance().getRegistrate(), "test", null)
            .name(() -> new StringTextComponent("test"))
            .description(() -> new StringTextComponent("testDescription"))
            .maxLevel(() -> 1)
            .createEntry();
}

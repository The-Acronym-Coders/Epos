package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.skill.Skill;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class PathRegistrar {

    public static final RegistryEntry<SkillSerializer> SKILL_SERIALIZER = Epos.instance().getRegistrate()
        .skillSerializer("path", () -> {
            Codec<Skill> codec = RecordCodecBuilder.create(instance -> instance
                .group(EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(Skill::getName),
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description")
                        .forGetter(Skill::getDescription),
                    Codec.intRange(1, 256).optionalFieldOf("maxLevel", 1).forGetter(Skill::getMaxLevel),
                    Codec.STRING.optionalFieldOf("expression", Skill.DEFAULT_SKILL_EXPRESSION)
                        .forGetter(Skill::getExpression))
                .apply(instance, Skill::new));
            return codec;
        }).register();

    public static final void register() {
    }
}

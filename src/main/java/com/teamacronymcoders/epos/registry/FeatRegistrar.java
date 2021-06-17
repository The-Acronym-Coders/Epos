package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.FeatSerializer;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.feat.Feat;
import com.teamacronymcoders.epos.skill.Skill;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class FeatRegistrar {

    public static final RegistryEntry<FeatSerializer> FEAT_SERIALIZER = Epos.instance().getRegistrate()
        .featSerializer("feat", () -> {
            Codec<Feat> codec = RecordCodecBuilder.create(instance -> instance
                .group(
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(Feat::getName),
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(Feat::getDescription),
                    Codec.BOOL.fieldOf("isAbility").forGetter(Feat::isAbility)
                )
                .apply(instance, Feat::new));
            return codec;
        }).register();

    public static final void register() {}
}

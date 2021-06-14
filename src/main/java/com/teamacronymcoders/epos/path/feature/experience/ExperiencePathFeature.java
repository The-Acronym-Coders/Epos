package com.teamacronymcoders.epos.path.feature.experience;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.minecraft.util.text.IFormattableTextComponent;

public class ExperiencePathFeature extends AbstractPathFeature {

    public static final Codec<ExperiencePathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(ExperiencePathFeature::getName),
            EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(ExperiencePathFeature::getDescription),
            Codec.INT.fieldOf("experience").forGetter(ExperiencePathFeature::getExperience)
        ).apply(instance, ExperiencePathFeature::new)
    );

    private final int experience;

    public ExperiencePathFeature(IFormattableTextComponent name, IFormattableTextComponent description, int experience) {
        super(name, description);
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public Codec<? extends IPathFeature> getCodec() {
        return CODEC;
    }
}

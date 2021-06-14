package com.teamacronymcoders.epos.path.feature.experience;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterStats;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

public class ExperiencePathFeature extends AbstractPathFeature {

    public static final Codec<ExperiencePathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(ExperiencePathFeature::getName),
            EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(ExperiencePathFeature::getDescription),
            Codec.BOOL.optionalFieldOf("skillExperience", false).forGetter(ExperiencePathFeature::isSkillExperience),
            ResourceLocation.CODEC.optionalFieldOf("skillID", null).forGetter(ExperiencePathFeature::getSkillID),
            Codec.INT.fieldOf("experience").forGetter(ExperiencePathFeature::getExperience)
        ).apply(instance, ExperiencePathFeature::new)
    );

    private final boolean skillExperience;
    private final ResourceLocation skillID;
    private final int experience;

    public ExperiencePathFeature(IFormattableTextComponent name, IFormattableTextComponent description,
                                 boolean skillExperience, ResourceLocation skillID,
                                 int experience) {
        super(name, description);
        this.skillExperience = skillExperience;
        this.skillID = skillID;
        this.experience = experience;
    }

    public boolean isSkillExperience() {
        return skillExperience;
    }

    public ResourceLocation getSkillID() {
        return skillID;
    }

    public int getExperience() {
        return experience;
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void applyTo(LivingEntity character, ICharacterStats stats) {
        if (character instanceof PlayerEntity) {
            if (isSkillExperience() && getSkillID() != null) {
                stats.getSkills().getOrCreate(getSkillID()).addExperience(getExperience());
            } else {
                stats.addExperience(experience);
            }
        }
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void removeFrom(LivingEntity character, ICharacterStats stats) {
        if (character instanceof PlayerEntity) {
            if (isSkillExperience() && getSkillID() != null) {
                stats.getSkills().getOrCreate(getSkillID()).removeExperience(getExperience());
            } else {
                stats.removeExperience(experience);
            }
        }
    }

    @Override
    public Codec<? extends IPathFeature> getCodec() {
        return CODEC;
    }
}

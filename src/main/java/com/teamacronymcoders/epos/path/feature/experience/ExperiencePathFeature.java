package com.teamacronymcoders.epos.path.feature.experience;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.path.features.PathFeatureSerializer;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.registry.PathFeatureRegistrar;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.teamacronymcoders.epos.util.EposRegistries;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

public class ExperiencePathFeature extends AbstractPathFeature {

    public static final Codec<ExperiencePathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(ExperiencePathFeature::getName),
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(ExperiencePathFeature::getDescription),
                    EposGrantType.CODEC.optionalFieldOf("grantType", EposGrantType.CHARACTER).forGetter(ExperiencePathFeature::getGrantType),
                    ResourceLocation.CODEC.optionalFieldOf("skillID", null).forGetter(ExperiencePathFeature::getSkillID),
                    Codec.INT.fieldOf("experience").forGetter(ExperiencePathFeature::getExperience)
            ).apply(instance, ExperiencePathFeature::new)
    );

    private final EposGrantType type;
    private final ResourceLocation skillID;
    private final int experience;

    public ExperiencePathFeature(IFormattableTextComponent name, IFormattableTextComponent description,
                                 EposGrantType type, ResourceLocation skillID,
                                 int experience) {
        super(name, description);
        this.type = type;
        this.skillID = skillID;
        this.experience = experience;
    }

    public EposGrantType getGrantType() {
        return type;
    }

    public ResourceLocation getSkillID() {
        return skillID;
    }

    public int getExperience() {
        return experience;
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void applyTo(LivingEntity character, ICharacterSheet stats) {
        if (character instanceof PlayerEntity) {
            if (this.getGrantType() == EposGrantType.SKILL && this.getSkillID() != null) {
                stats.getSkills().getOrCreate(getSkillID()).addExperience(this.getExperience());
            } else {
                stats.addExperience(experience);
            }
        }
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void removeFrom(LivingEntity character, ICharacterSheet stats) {
        if (character instanceof PlayerEntity) {
            if (getGrantType() == EposGrantType.SKILL && getSkillID() != null) {
                stats.getSkills().getOrCreate(getSkillID()).removeExperience(getExperience());
            } else {
                stats.removeExperience(experience);
            }
        }
    }

    @Override
    public ICodecEntry<? extends IPathFeature, ?> codec() {
        return PathFeatureRegistrar.EXPERIENCE_FEATURE_SERIALIZER.get();
    }
}

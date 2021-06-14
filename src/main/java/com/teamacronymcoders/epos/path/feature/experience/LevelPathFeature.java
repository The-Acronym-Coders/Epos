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

public class LevelPathFeature extends AbstractPathFeature {

    public static final Codec<LevelPathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(LevelPathFeature::getName),
            EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(LevelPathFeature::getDescription),
            EposGrantType.CODEC.optionalFieldOf("grantType", EposGrantType.CHARACTER).forGetter(LevelPathFeature::getGrantType),
            ResourceLocation.CODEC.optionalFieldOf("skillID", null).forGetter(LevelPathFeature::getSkillID),
            Codec.INT.fieldOf("levels").forGetter(LevelPathFeature::getLevels)
        ).apply(instance, LevelPathFeature::new)
    );

    private final EposGrantType type;
    private final ResourceLocation skillID;
    private final int levels;

    public LevelPathFeature(IFormattableTextComponent name, IFormattableTextComponent description,
                            EposGrantType type, ResourceLocation skillID,
                            int levels) {
        super(name, description);
        this.type = type;
        this.skillID = skillID;
        this.levels = levels;
    }

    public EposGrantType getGrantType() {
        return this.type;
    }

    public ResourceLocation getSkillID() {
        return this.skillID;
    }

    public int getLevels() {
        return this.levels;
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void applyTo(LivingEntity character, ICharacterStats stats) {
        if (character instanceof PlayerEntity) {
            if (this.getGrantType() == EposGrantType.SKILL && getSkillID() != null) {
                //stats.getSkills().getOrCreate(getSkillID()).removeExperience(getExperience());
            } else {
                //stats.addExperience(experience);
            }
        }
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void removeFrom(LivingEntity character, ICharacterStats stats) {
        if (character instanceof PlayerEntity) {
            if (getGrantType() == EposGrantType.SKILL && getSkillID() != null) {
                //stats.getSkills().getOrCreate(getSkillID()).removeExperience(getExperience());
            } else {
                //stats.removeExperience(experience);
            }
        }
    }

    @Override
    public Codec<? extends IPathFeature> getCodec() {
        return CODEC;
    }
}

package com.teamacronymcoders.epos.path.feature.point;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.character.info.PointInfo;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.path.features.PathFeatureSerializer;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.registry.PathFeatureRegistrar;
import com.teamacronymcoders.epos.skill.Skill;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

import javax.annotation.Nullable;

public class PointPathFeature extends AbstractPathFeature {

    public static final Codec<PointPathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(PointPathFeature::getName),
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(PointPathFeature::getDescription),
                    EposPointTypes.CODEC.optionalFieldOf("type", EposPointTypes.PATH).forGetter(PointPathFeature::getType),
                    Codec.INT.optionalFieldOf("amount", 0).forGetter(PointPathFeature::getAmount)
            ).apply(instance, PointPathFeature::new)
    );

    private final EposPointTypes type;
    private final int amount;

    public PointPathFeature(IFormattableTextComponent name, IFormattableTextComponent description, EposPointTypes type, int amount) {
        super(name, description);
        this.type = type;
        this.amount = amount;
    }

    public EposPointTypes getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterSheet stats) {
        if (character instanceof PlayerEntity) {
            PointInfo info = stats.getCharacterInfo().getPointInfo();
            switch (type) {
                case PATH:
                    info.addPathPoints(this.amount);
                case SKILL:
                    info.addSkillPoints(this.amount);
                case FEAT:
                    info.addFeatPoints(this.amount);
            }
        }
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterSheet stats) {
        if (character instanceof PlayerEntity) {
            PointInfo info = stats.getCharacterInfo().getPointInfo();
            switch (type) {
                case PATH:
                    info.removePathPoints(this.amount);
                case SKILL:
                    info.removeSkillPoints(this.amount);
                case FEAT:
                    info.removeFeatPoints(this.amount);
            }
        }
    }

    @Override
    public ICodecEntry<? extends IPathFeature, ?> codec() {
        return PathFeatureRegistrar.EXPERIENCE_FEATURE_SERIALIZER.get();
    }
}

package com.teamacronymcoders.epos.path.feature.grant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.enums.EposGrantType;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

public class GrantPathFeature extends AbstractPathFeature {

    public static final Codec<GrantPathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("name").forGetter(GrantPathFeature::getName),
                    EposCodecs.FORMATTABLE_TEXT_COMPONENT.fieldOf("description").forGetter(GrantPathFeature::getDescription),
                    EposGrantType.CODEC.optionalFieldOf("type", EposGrantType.PATH).forGetter(GrantPathFeature::getType),
                    ResourceLocation.CODEC.optionalFieldOf("id", new ResourceLocation("epos:empty_path_feature")).forGetter(GrantPathFeature::getId)
            ).apply(instance, GrantPathFeature::new)
    );

    public final EposGrantType type;
    public final ResourceLocation id;

    public GrantPathFeature(IFormattableTextComponent name, IFormattableTextComponent description, EposGrantType type, ResourceLocation id) {
        super(name, description);
        this.type = type;
        this.id = id;
    }

    @Override
    public void applyTo(LivingEntity character, ICharacterSheet stats) {
        switch (type) {
            case PATH:
                return;
            case SKILL: {
                SkillInfo skill = stats.getSkills().getOrCreate(this.id);
                if (skill.getLevel() == 0) skill.setLevel(1);
            }
            case FEAT: {
                FeatInfo feat = stats.getFeats().getOrCreate(this.id);
            }
        }
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterSheet stats) {
        switch (type) {
            case PATH:
                return;
            case SKILL: {
                stats.getSkills().getSkillInfoMap().remove(this.id);
            }
            case FEAT: {
                stats.getFeats().getFeatInfoMap().remove(this.id);
            }
        }
    }

    @Override
    public Codec<? extends IPathFeature> getCodec() {
        return CODEC;
    }

    public EposGrantType getType() {
        return type;
    }

    public ResourceLocation getId() {
        return id;
    }
}

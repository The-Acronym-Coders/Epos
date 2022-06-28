package com.teamacronymcoders.epos.path.feature.grant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.enums.EposGrantType;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.registry.PathFeatureRegistrar;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class GrantPathFeature extends AbstractPathFeature {

    public static final Codec<GrantPathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(GrantPathFeature::getName),
                    EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(GrantPathFeature::getDescription),
                    EposGrantType.CODEC.optionalFieldOf("type", EposGrantType.PATH).forGetter(GrantPathFeature::getType),
                    ResourceLocation.CODEC.optionalFieldOf("id", new ResourceLocation("epos:empty_path_feature")).forGetter(GrantPathFeature::getId)
            ).apply(instance, GrantPathFeature::new)
    );

    public final EposGrantType type;
    public final ResourceLocation id;

    public GrantPathFeature(MutableComponent name, MutableComponent description, EposGrantType type, ResourceLocation id) {
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
    public Codec<? extends IPathFeature> codec() {
        return PathFeatureRegistrar.GRANT_FEATURE_SERIALIZER.get();
    }

    public EposGrantType getType() {
        return type;
    }

    public ResourceLocation getId() {
        return id;
    }


}

package com.teamacronymcoders.epos.path.feature.experience;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.registry.PathFeatureRegistrar;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class LevelPathFeature extends AbstractPathFeature {

    public static final Codec<LevelPathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(LevelPathFeature::getName),
                    EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(LevelPathFeature::getDescription),
                    EposGrantType.CODEC.optionalFieldOf("grantType", EposGrantType.CHARACTER).forGetter(LevelPathFeature::getGrantType),
                    ResourceLocation.CODEC.optionalFieldOf("skillID", null).forGetter(LevelPathFeature::getSkillID),
                    Codec.INT.fieldOf("levels").forGetter(LevelPathFeature::getLevels)
            ).apply(instance, LevelPathFeature::new)
    );

    private final EposGrantType type;
    private final ResourceLocation skillID;
    private final int levels;

    public LevelPathFeature(MutableComponent name, MutableComponent description, EposGrantType type, @Nullable ResourceLocation skillID, int levels) {
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

    @Override
    public void applyTo(LivingEntity character, ICharacterSheet stats) {
        if (character instanceof Player) {
            if (this.getGrantType() == EposGrantType.SKILL && this.getSkillID() != null) {
                SkillInfo info = stats.getSkills().getOrCreate(this.getSkillID());
                int currentLevel = info.getLevel();
                info.setLevel(Math.min(currentLevel + this.levels, info.getMaxLevel()));
            } else {
                stats.levelUp(this.levels);
            }
        }
    }

    @Override
    public void removeFrom(LivingEntity character, ICharacterSheet stats) {
        if (character instanceof Player) {
            if (getGrantType() == EposGrantType.SKILL) {
                SkillInfo info = stats.getSkills().getOrCreate(this.getSkillID());
                int currentLevel = info.getLevel();
                info.setLevel(Math.max(currentLevel + this.levels, 0));
            } else {
                stats.levelDown(this.levels);
            }
        }
    }

    @Override
    public ICodecEntry<? extends IPathFeature, ?> codec() {
        return PathFeatureRegistrar.EXPERIENCE_FEATURE_SERIALIZER.get();
    }
}

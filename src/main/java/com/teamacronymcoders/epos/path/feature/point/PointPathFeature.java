package com.teamacronymcoders.epos.path.feature.point;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.character.info.PointInfo;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.path.feature.AbstractPathFeature;
import com.teamacronymcoders.epos.registry.PathFeatureRegistrar;
import com.teamacronymcoders.epos.util.EposCodecs;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PointPathFeature extends AbstractPathFeature {

    public static final Codec<PointPathFeature> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(PointPathFeature::getName),
                    EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(PointPathFeature::getDescription),
                    EposPointTypes.CODEC.optionalFieldOf("type", EposPointTypes.PATH).forGetter(PointPathFeature::getType),
                    Codec.INT.optionalFieldOf("amount", 0).forGetter(PointPathFeature::getAmount)
            ).apply(instance, PointPathFeature::new)
    );

    private final EposPointTypes type;
    private final int amount;

    public PointPathFeature(MutableComponent name, MutableComponent description, EposPointTypes type, int amount) {
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
        if (character instanceof Player) {
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
        if (character instanceof Player) {
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
    public Codec<? extends IPathFeature> codec() {
        return PathFeatureRegistrar.POINT_FEATURE_SERIALIZER.get();
    }


}

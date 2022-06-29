package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.ashwork.dynamicregistries.entry.DynamicEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class SpiritualAid implements ISpiritualAid {

    private final NonNullSupplier<ResourceLocation> entityId;
    private final NonNullSupplier<List<MobEffectInstance>> effects;

    public SpiritualAid(ResourceLocation entityId, List<MobEffectInstance> effects) {
        this.entityId = () -> entityId;
        this.effects = () -> effects;
    }

    public SpiritualAid(NonNullSupplier<ResourceLocation> entityId, NonNullSupplier<List<MobEffectInstance>> effects) {
        this.entityId = entityId;
        this.effects = effects;
    }

    @Override
    public ResourceLocation getEntityId() {
        return this.entityId.get();
    }

    @Override
    public List<MobEffectInstance> getEffects() {
        return this.effects.get();
    }

    @Override
    public Codec<? extends ISpiritualAid> codec() {
        return SpiritualAidRegistrar.SPIRITUAL_AID_SERIALIZER.get();
    }


}

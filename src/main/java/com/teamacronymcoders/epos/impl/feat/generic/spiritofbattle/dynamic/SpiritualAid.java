package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import net.ashwork.dynamicregistries.entry.DynamicEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class SpiritualAid extends DynamicEntry<ISpiritualAid> implements ISpiritualAid {

    private final ResourceLocation entityId;
    private final List<EffectInstance> effects;

    public SpiritualAid(ResourceLocation entityId, List<EffectInstance> effects) {
        this.entityId = entityId;
        this.effects = effects;
    }

    @Override
    public ResourceLocation getEntityId() {
        return this.entityId;
    }

    @Override
    public List<EffectInstance> getEffects() {
        return this.effects;
    }

    @Override
    public ICodecEntry<? extends ISpiritualAid, ?> codec() {
        return SpiritualAidRegistrar.SPIRITUAL_AID_SERIALIZER.get();
    }

}

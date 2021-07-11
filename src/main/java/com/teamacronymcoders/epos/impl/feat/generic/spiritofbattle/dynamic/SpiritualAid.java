package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import net.ashwork.dynamicregistries.entry.DynamicEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.potion.EffectInstance;

import java.util.List;

public class SpiritualAid extends DynamicEntry<ISpiritualAid> implements ISpiritualAid {

    private final List<EffectInstance> effects;

    public SpiritualAid(List<EffectInstance> effects) {
        this.effects = effects;
    }

    @Override
    public List<EffectInstance> getEffects() {
        return effects;
    }

    @Override
    public ICodecEntry<? extends ISpiritualAid, ?> codec() {
        return SpiritualAidRegistrar.SPIRITUAL_AID_SERIALIZER.get();
    }

}

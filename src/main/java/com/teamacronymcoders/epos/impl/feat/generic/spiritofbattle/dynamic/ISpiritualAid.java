package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import net.ashwork.dynamicregistries.entry.IDynamicEntry;
import net.minecraft.potion.EffectInstance;

import java.util.List;

public interface ISpiritualAid extends IDynamicEntry<ISpiritualAid> {
    List<EffectInstance> getEffects();
}

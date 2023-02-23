package com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic;

import net.ashwork.dynamicregistries.entry.IDynamicEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public interface ISpiritualAid extends IDynamicEntry<ISpiritualAid> {

    ResourceLocation getEntityId();

    List<MobEffectInstance> getEffects();

}

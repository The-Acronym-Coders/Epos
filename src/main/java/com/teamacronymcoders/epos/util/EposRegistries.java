package com.teamacronymcoders.epos.util;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.FeatSerializer;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.PathSerializer;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.SpiritualAidSerializer;
import net.ashwork.dynamicregistries.DynamicRegistryManager;
import net.ashwork.dynamicregistries.registry.DynamicRegistry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EposRegistries {
    public final DynamicRegistry<IPath, PathSerializer> PATH_REGISTRY = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "path"));
    public final DynamicRegistry<ISkill, SkillSerializer> SKILL_REGISTRY = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "skill"));
    public final DynamicRegistry<IFeat, FeatSerializer> FEAT_REGISTRY = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "feat"));
    public final DynamicRegistry<ISpiritualAid, SpiritualAidSerializer> SPIRITUAL_AID_REGISTRY = DynamicRegistryManager.STATIC.getRegistry(new ResourceLocation(Epos.ID, "spiritual_aid"));

    @Nullable
    public IPath getPath(@Nonnull ResourceLocation pathId) {
        return PATH_REGISTRY != null ? PATH_REGISTRY.getValue(pathId) : null;
    }

    @Nullable
    public ISkill getSkill(@Nonnull ResourceLocation pathId) {
        return SKILL_REGISTRY != null ? SKILL_REGISTRY.getValue(pathId) : null;
    }

    @Nullable
    public IFeat getFeat(@Nonnull ResourceLocation pathId) {
        return FEAT_REGISTRY != null ? FEAT_REGISTRY.getValue(pathId) : null;
    }

    @Nullable
    public ISpiritualAid getSpiritualAid(@Nonnull ResourceLocation pathId) {
        return SPIRITUAL_AID_REGISTRY != null ? SPIRITUAL_AID_REGISTRY.getValue(pathId) : null;
    }

}

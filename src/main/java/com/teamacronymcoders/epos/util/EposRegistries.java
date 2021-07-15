package com.teamacronymcoders.epos.util;

import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.FeatSerializer;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.PathSerializer;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.path.features.PathFeatureSerializer;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.SpiritualAidSerializer;
import net.ashwork.dynamicregistries.DynamicRegistryManager;
import net.ashwork.dynamicregistries.registry.DynamicRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EposRegistries {
    public static final ResourceLocation PATH_REGISTRY_ID = new ResourceLocation(Epos.ID, "path");
    public static final ResourceLocation SKILL_REGISTRY_ID = new ResourceLocation(Epos.ID, "skill");
    public static final ResourceLocation FEAT_REGISTRY_ID = new ResourceLocation(Epos.ID, "feat");
    public static final ResourceLocation PATH_FEATURE_REGISTRY_ID = new ResourceLocation(Epos.ID, "path_feature");
    public static final ResourceLocation SPIRITUAL_AID_REGISTRY_ID = new ResourceLocation(Epos.ID, "spiritual_aid");

    public static final ResourceLocation MISSING_ENTRY = new ResourceLocation(Epos.ID, "missing");

    public final Lazy<DynamicRegistry<IPath, PathSerializer>> PATH_REGISTRY = Lazy.of(() -> DynamicRegistryManager.DYNAMIC.getRegistry(PATH_REGISTRY_ID));
    public final Lazy<DynamicRegistry<ISkill, SkillSerializer>> SKILL_REGISTRY = Lazy.of(() -> DynamicRegistryManager.DYNAMIC.getRegistry(SKILL_REGISTRY_ID));
    public final Lazy<DynamicRegistry<IFeat, FeatSerializer>> FEAT_REGISTRY = Lazy.of(() -> DynamicRegistryManager.DYNAMIC.getRegistry(FEAT_REGISTRY_ID));
    public final Lazy<DynamicRegistry<IPathFeature, PathFeatureSerializer>> PATH_FEATURE_REGISTRY = Lazy.of(() -> DynamicRegistryManager.DYNAMIC.getRegistry(PATH_FEATURE_REGISTRY_ID));
    public final Lazy<DynamicRegistry<ISpiritualAid, SpiritualAidSerializer>> SPIRITUAL_AID_REGISTRY = Lazy.of(() -> DynamicRegistryManager.DYNAMIC.getRegistry(SPIRITUAL_AID_REGISTRY_ID));

    @Nullable
    public IPath getPath(@Nonnull ResourceLocation pathId) {
        return PATH_REGISTRY.get().getValue(pathId);
    }

    @Nullable
    public ISkill getSkill(@Nonnull ResourceLocation skillId) {
        return SKILL_REGISTRY.get().getValue(skillId);
    }

    @Nullable
    public IFeat getFeat(@Nonnull ResourceLocation featId) {
        return FEAT_REGISTRY.get().getValue(featId);
    }

    @Nullable
    public IPathFeature getPathFeature(@Nonnull ResourceLocation pathFeatureId) {
        return PATH_FEATURE_REGISTRY.get().getValue(pathFeatureId);
    }

    @Nullable
    public ISpiritualAid getSpiritualAid(@Nonnull ResourceLocation spiritualAidId) {
        return SPIRITUAL_AID_REGISTRY.get().getValue(spiritualAidId);
    }
}

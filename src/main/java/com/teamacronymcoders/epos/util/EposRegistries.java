package com.teamacronymcoders.epos.util;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.impl.feat.generic.spiritofbattle.dynamic.ISpiritualAid;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class EposRegistries {

    static { init(); }

    public static final ResourceLocation MISSING_ENTRY = new ResourceLocation(Epos.ID, "missing");

    // Path
    static final DeferredRegister<IPath> DEFERRED_PATHS = DeferredRegister.create(Keys.PATHS, Epos.ID);
    public static final Supplier<IForgeRegistry<IPath>> PATHS = DEFERRED_PATHS.makeRegistry(() -> new RegistryBuilder<IPath>().disableSaving().disableSync().dataPackRegistry(IPath.DIRECT_CODEC).setDefaultKey(MISSING_ENTRY));

    // Skills
    static final DeferredRegister<ISkill> DEFERRED_SKILLS = DeferredRegister.create(Keys.SKILLS, Epos.ID);
    public static final Supplier<IForgeRegistry<ISkill>> SKILLS = DEFERRED_SKILLS.makeRegistry(() -> new RegistryBuilder<ISkill>().disableSaving().disableSync().dataPackRegistry(ISkill.DIRECT_CODEC).setDefaultKey(MISSING_ENTRY));

    // Feats
    static final DeferredRegister<IFeat> DEFERRED_FEATS = DeferredRegister.create(Keys.FEATS, Epos.ID);
    public static final Supplier<IForgeRegistry<IFeat>> FEATS = DEFERRED_FEATS.makeRegistry(() -> new RegistryBuilder<IFeat>().disableSaving().disableSync().dataPackRegistry(IFeat.DIRECT_CODEC).setDefaultKey(MISSING_ENTRY));

    // Feat Infos
    static final DeferredRegister<FeatInfo> DEFERRED_FEAT_INFO = DeferredRegister.create(Keys.FEAT_INFOS, Epos.ID);
    public static final Supplier<IForgeRegistry<FeatInfo>> FEAT_INFOS = DEFERRED_FEAT_INFO.makeRegistry(() -> new RegistryBuilder<FeatInfo>().disableSaving().disableSync());

    // Path Features
    static final DeferredRegister<IPathFeature> DEFERRED_PATH_FEATURES = DeferredRegister.create(Keys.PATH_FEATURES, Epos.ID);
    public static final Supplier<IForgeRegistry<IPathFeature>> PATH_FEATURES = DEFERRED_PATH_FEATURES.makeRegistry(() -> new RegistryBuilder<IPathFeature>().disableSaving().disableSync().dataPackRegistry(IPathFeature.DIRECT_CODEC).setDefaultKey(MISSING_ENTRY));

    // Path Serializers
    static final DeferredRegister<Codec<? extends IPath>> DEFERRED_PATH_SERIALIZERS = DeferredRegister.create(Keys.PATH_SERIALIZERS, Epos.ID);
    public static final Supplier<IForgeRegistry<Codec<? extends IPath>>> PATH_SERIALIZERS = DEFERRED_PATH_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends IPath>>().disableSaving().disableSync());

    // Skill Serializers
    static final DeferredRegister<Codec<? extends ISkill>> DEFERRED_SKILL_SERIALIZERS = DeferredRegister.create(Keys.SKILL_SERIALIZERS, Epos.ID);
    public static final Supplier<IForgeRegistry<Codec<? extends ISkill>>> SKILL_SERIALIZERS = DEFERRED_SKILL_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends ISkill>>().disableSaving().disableSync());

    // Feat Serializers
    static final DeferredRegister<Codec<? extends IFeat>> DEFERRED_FEAT_SERIALIZERS = DeferredRegister.create(Keys.FEAT_SERIALIZERS, Epos.ID);
    public static final Supplier<IForgeRegistry<Codec<? extends IFeat>>> FEAT_SERIALIZERS = DEFERRED_FEAT_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends IFeat>>().disableSaving().disableSync());

    // Path Feature Seralizers
    static final DeferredRegister<Codec<? extends IPathFeature>> DEFERRED_PATH_FEATURE_SERIALIZERS = DeferredRegister.create(Keys.PATH_FEATURE_SERIALIZERS, Epos.ID);
    public static final Supplier<IForgeRegistry<Codec<? extends IPathFeature>>> PATH_FEATURE_SERIALIZERS = DEFERRED_PATH_FEATURE_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends IPathFeature>>().disableSaving().disableSync());

    // Spiritual Aids
    static final DeferredRegister<ISpiritualAid> DEFERRED_SPIRITUAL_AIDS = DeferredRegister.create(Keys.SPIRITUAL_AIDS, Epos.ID);
    public static final Supplier<IForgeRegistry<ISpiritualAid>> SPIRITUAL_AIDS = DEFERRED_SPIRITUAL_AIDS.makeRegistry(() -> new RegistryBuilder<ISpiritualAid>().disableSaving().disableSync().dataPackRegistry(ISpiritualAid.DIRECT_CODEC).setDefaultKey(MISSING_ENTRY));
    static final DeferredRegister<Codec<? extends ISpiritualAid>> DEFERRED_SPIRITUAL_AID_SERIALIZERS = DeferredRegister.create(Keys.SPIRITUAL_AID_SERIALIZERS, Epos.ID);
    public static final Supplier<IForgeRegistry<Codec<? extends ISpiritualAid>>> SPIRITUAL_AID_SERIALIZERS = DEFERRED_SPIRITUAL_AID_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends ISpiritualAid>>().disableSaving().disableSync());


    @Nullable
    public IPath getPath(@Nonnull ResourceLocation pathId) {
        return PATHS.get().getValue(pathId);
    }

    @Nullable
    public Codec<? extends IPath> getPathCodec(@Nonnull ResourceLocation codecId) {
        return PATH_SERIALIZERS.get().getValue(codecId);
    }

    @Nullable
    public ISkill getSkill(@Nonnull ResourceLocation skillId) {
        return SKILLS.get().getValue(skillId);
    }

    @Nullable
    public Codec<? extends ISkill> getSkillCodec(@Nonnull ResourceLocation codecId) {
        return SKILL_SERIALIZERS.get().getValue(codecId);
    }

    @Nullable
    public IFeat getFeat(@Nonnull ResourceLocation featId) {
        return FEATS.get().getValue(featId);
    }
    @Nullable
    public FeatInfo getFeatInfo(@Nonnull ResourceLocation infoId) {
        return FEAT_INFOS.get().getValue(infoId);
    }
    @Nullable
    public Codec<? extends IFeat> getFeatCodec(@Nonnull ResourceLocation codecId) {
        return FEAT_SERIALIZERS.get().getValue(codecId);
    }

    @Nullable
    public IPathFeature getPathFeature(@Nonnull ResourceLocation pathFeatureId) {
        return PATH_FEATURES.get().getValue(pathFeatureId);
    }

    @Nullable
    public Codec<? extends IPathFeature> getPathFeatureCodec(@Nonnull ResourceLocation codecId) {
        return PATH_FEATURE_SERIALIZERS.get().getValue(codecId);
    }

    @Nullable
    public ISpiritualAid getSpiritualAid(@Nonnull ResourceLocation spiritualAidId) {
        return SPIRITUAL_AIDS.get().getValue(spiritualAidId);
    }

    public static final class Keys {
        public static final ResourceKey<Registry<IPath>> PATHS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "path"));
        public static final ResourceKey<Registry<Codec<? extends IPath>>> PATH_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "path_serializers"));

        public static final ResourceKey<Registry<ISkill>> SKILLS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "skill"));
        public static final ResourceKey<Registry<Codec<? extends ISkill>>> SKILL_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "skill_serializers"));

        public static final ResourceKey<Registry<IFeat>> FEATS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "feat"));
        public static final ResourceKey<Registry<FeatInfo>> FEAT_INFOS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "feat_infos"));
        public static final ResourceKey<Registry<Codec<? extends IFeat>>> FEAT_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "feat_serializers"));

        public static final ResourceKey<Registry<IPathFeature>> PATH_FEATURES = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "path_feature"));
        public static final ResourceKey<Registry<Codec<? extends IPathFeature>>> PATH_FEATURE_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "path_feature_serializers"));

        public static final ResourceKey<Registry<ISpiritualAid>> SPIRITUAL_AIDS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "spiritual_aid"));
        public static final ResourceKey<Registry<Codec<? extends ISpiritualAid>>> SPIRITUAL_AID_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "spiritual_aid_serializers"));

        private static void init() {}
    }

    private static void init() {
        Keys.init();
    }
}

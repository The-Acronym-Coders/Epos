package com.teamacronymcoders.epos.api.registry;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.EposAPI;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.FeatInfo;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.SkillInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class EposAPIRegistries {
  public static class Keys {

    /**
     * <p>Key associated with the {@link FeatInfo} Registry.</p>
     */
    public static final ResourceKey<Registry<SkillInfo>> SKILL_INFOS = ResourceKey.createRegistryKey(new ResourceLocation(EposAPI.MOD_ID, "feat_infos"));

    /**
     * <p>Key associated with the {@link FeatInfo} Codec Registry.</p>
     */
    public static final ResourceKey<Registry<Codec<? extends SkillInfo>>> SKILL_INFO_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(EposAPI.MOD_ID, "feat_info_serializers"));


    /**
     * <p>Key associated with the {@link FeatInfo} Registry.</p>
     */
    public static final ResourceKey<Registry<FeatInfo>> FEAT_INFOS = ResourceKey.createRegistryKey(new ResourceLocation(EposAPI.MOD_ID, "feat_infos"));

    /**
     * <p>Key associated with the {@link FeatInfo} Codec Registry.</p>
     */
    public static final ResourceKey<Registry<Codec<? extends FeatInfo>>> FEAT_INFO_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(EposAPI.MOD_ID, "feat_info_serializers"));

  }
}

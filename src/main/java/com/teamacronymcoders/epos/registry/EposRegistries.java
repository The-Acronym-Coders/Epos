package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.FeatInfo;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.SkillInfo;
import com.teamacronymcoders.epos.api.registry.EposAPIRegistries;
import com.teamacronymcoders.epos.charactersheet.storage.info.ExtendableFeatInfo;
import com.teamacronymcoders.epos.charactersheet.storage.info.ExtendableSkillInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class EposRegistries {

  // Skill Info
  static final DeferredRegister<SkillInfo> DEFERRED_SKILL_INFOS = DeferredRegister.create(EposAPIRegistries.Keys.SKILL_INFOS, Epos.MOD_ID);
  public static final Supplier<IForgeRegistry<SkillInfo>> SKILL_INFOS = DEFERRED_SKILL_INFOS.makeRegistry(() -> new RegistryBuilder<SkillInfo>().disableSaving().disableSync().dataPackRegistry(ExtendableSkillInfo.DIRECT_CODEC));

  static final DeferredRegister<Codec<? extends SkillInfo>> DEFERRED_SKILL_INFO_SERIALIZERS = DeferredRegister.create(EposAPIRegistries.Keys.SKILL_INFO_SERIALIZERS, Epos.MOD_ID);
  public static final Supplier<IForgeRegistry<Codec<? extends SkillInfo>>> SKILL_INFO_SERIALIZERS = DEFERRED_SKILL_INFO_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends SkillInfo>>().disableSaving().disableSync());

  // FeatInfo
  static final DeferredRegister<FeatInfo> DEFERRED_FEAT_INFOS = DeferredRegister.create(EposAPIRegistries.Keys.FEAT_INFOS, Epos.MOD_ID);
  public static final Supplier<IForgeRegistry<FeatInfo>> FEAT_INFOS = DEFERRED_FEAT_INFOS.makeRegistry(() -> new RegistryBuilder<FeatInfo>().disableSaving().disableSync().dataPackRegistry(ExtendableFeatInfo.DIRECT_CODEC));

  static final DeferredRegister<Codec<? extends FeatInfo>> DEFERRED_FEAT_INFO_SERIALIZERS = DeferredRegister.create(EposAPIRegistries.Keys.FEAT_INFO_SERIALIZERS, Epos.MOD_ID);
  public static final Supplier<IForgeRegistry<Codec<? extends FeatInfo>>> FEAT_INFO_SERIALIZERS = DEFERRED_FEAT_INFO_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends FeatInfo>>().disableSaving().disableSync());

}

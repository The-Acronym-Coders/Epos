package com.teamacronymcoders.epos.api.registry;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.IExtendableFeatInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class EposRegistries {

  static final DeferredRegister<IExtendableFeatInfo> DEFERRED_FEAT_INFOS = DeferredRegister.create(Keys.FEAT_INFOS, Epos.ID);
  public static final Supplier<IForgeRegistry<IExtendableFeatInfo>> FEAT_INFOS = DEFERRED_FEAT_INFOS.makeRegistry(() -> new RegistryBuilder<IExtendableFeatInfo>().disableSaving().disableSync().dataPackRegistry(IExtendableFeatInfo.DIRECT_CODEC));

  static final DeferredRegister<Codec<? extends IExtendableFeatInfo>> DEFERRED_FEAT_INFO_SERIALIZERS = DeferredRegister.create(Keys.FEAT_SERIALIZERS, Epos.ID);
  public static final Supplier<IForgeRegistry<Codec<? extends IExtendableFeatInfo>>> FEAT_INFO_SERIALIZERS = DEFERRED_FEAT_INFO_SERIALIZERS.makeRegistry(() -> new RegistryBuilder<Codec<? extends IExtendableFeatInfo>>().disableSaving().disableSync());

  private static class Keys {

    public static final ResourceKey<Registry<IExtendableFeatInfo>> FEAT_INFOS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "feat_infos"));
    public static final ResourceKey<Registry<Codec<? extends IExtendableFeatInfo>>> FEAT_SERIALIZERS = ResourceKey.createRegistryKey(new ResourceLocation(Epos.ID, "feat_info_serializers"));

  }
}

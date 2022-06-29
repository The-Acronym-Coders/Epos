package com.teamacronymcoders.epos.charactersheet.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.charactersheet.storage.IPathStorage;
import com.teamacronymcoders.epos.api.charactersheet.storage.info.PathInfo;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record PathStorage(Map<ResourceLocation, PathInfo> paths) implements IPathStorage {

  public static final Codec<PathStorage> CODEC = RecordCodecBuilder.create(instance -> instance
          .group(Codec.unboundedMap(ResourceLocation.CODEC, PathInfo.CODEC).fieldOf("paths").forGetter(PathStorage::paths))
          .apply(instance, PathStorage::new));

  @Override
  public PathInfo getPath(ResourceLocation pathId) {
    return this.getOrCreate(pathId);
  }

  private PathInfo getOrCreate(ResourceLocation pathId) {
    return this.paths.computeIfAbsent(pathId, (rl) -> new PathInfo(0, false));
  }

  @Override
  public void levelUpPath(ResourceLocation pathId, int levels) {
    PathInfo info = this.getPath(pathId);
    info.levelUp(levels);
  }

  @Override
  public void levelDownPath(ResourceLocation pathId, int levels) {
    PathInfo info = this.getPath(pathId);
    info.levelDown(levels);
  }

}

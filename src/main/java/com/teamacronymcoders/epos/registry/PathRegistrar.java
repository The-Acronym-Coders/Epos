package com.teamacronymcoders.epos.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.path.Path;
import com.teamacronymcoders.epos.util.EposCodecs;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class PathRegistrar {

    public static void register() {}

    public static final RegistryEntry<Codec<? extends IPath>> PATH_SERIALIZER = Epos.instance().getRegistrate()
            .pathSerializer("default_path_serializer", () -> {
                Codec<Path> codec = RecordCodecBuilder.create(instance -> instance
                        .group(
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("name").forGetter(Path::getName),
                                EposCodecs.MUTABLE_COMPONENT_CODEC.fieldOf("description").forGetter(Path::getDescription),
                                Codec.intRange(0, 99).optionalFieldOf("maxLevel", 0).forGetter(Path::getMaxLevel),
                                PathFeatures.CODEC.optionalFieldOf("features", new PathFeatures()).forGetter(Path::getPathFeatures))
                        .apply(instance, Path::new));
                return codec;
            });

}

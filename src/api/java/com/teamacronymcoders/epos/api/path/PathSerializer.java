package com.teamacronymcoders.epos.api.path;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import com.teamacronymcoders.epos.api.skill.SkillSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public final class PathSerializer extends ForgeRegistryEntry<PathSerializer> implements ISerializer<IPath, SkillSerializer> {

    private final Codec<? extends IPath> codec;

    public PathSerializer(Codec<? extends IPath> codec) {
        this.codec = codec;
    }

    @Override
    public Codec<? extends IPath> objectCodec() {
        return this.codec;
    }
}

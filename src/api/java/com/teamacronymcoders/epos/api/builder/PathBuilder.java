package com.teamacronymcoders.epos.api.builder;

import com.mojang.datafixers.util.Function4;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.path.Path;
import com.tterrag.registrate.util.nullness.NonnullType;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.NonNullSupplier;

import java.util.*;

public class PathBuilder {

    public static final Map<ResourceLocation, IPath> BUILT_PATHS = new HashMap<>();

    public static PathBuilder create(ResourceLocation registryName) {
        return new PathBuilder(registryName, Path::new);
    }

    private final ResourceLocation registryName;
    private final Function4<MutableComponent, MutableComponent, Integer, PathFeatures, IPath> factory;
    private NonNullSupplier<MutableComponent> name;
    private NonNullSupplier<MutableComponent> description;
    private NonNullSupplier<Integer> maxLevel = () -> 1;
    private final Int2ObjectArrayMap<List<IPathFeature>> pathFeatures;

    private PathBuilder(ResourceLocation registryName, Function4<MutableComponent, MutableComponent, Integer, PathFeatures, IPath> factory) {
        this.registryName = registryName;
        this.factory = factory;
        this.pathFeatures = new Int2ObjectArrayMap<>();
    }

    public PathBuilder name(NonNullSupplier<MutableComponent> name) {
        this.name = name;
        return this;
    }

    public PathBuilder description(NonNullSupplier<MutableComponent> description) {
        this.description = description;
        return this;
    }

    public PathBuilder maxLevel(NonNullSupplier<Integer> maxLevel) {
        this.maxLevel = maxLevel;
        return this;
    }

    public PathBuilder withPathFeatureForLevel(int level, IPathFeature pathFeature) {
        this.pathFeatures.computeIfAbsent(level, integer -> new ArrayList<>()).add(pathFeature);
        return this;
    }

    public PathBuilder withPathFeatures(int level, IPathFeature... pathFeatures) {
        this.pathFeatures.computeIfAbsent(level, (integer) -> new ArrayList<>()).addAll(Arrays.asList(pathFeatures));
        return this;
    }

    @NonnullType
    public IPath createEntry() {
        IPath path = factory.apply(this.name.get(), this.description.get(), this.maxLevel.get(), new PathFeatures(this.pathFeatures));
        BUILT_PATHS.put(this.registryName, path);
        return path;
    }

}

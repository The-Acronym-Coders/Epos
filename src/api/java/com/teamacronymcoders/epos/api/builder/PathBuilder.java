package com.teamacronymcoders.epos.api.builder;

import com.mojang.datafixers.util.Function4;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.features.IPathFeature;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.path.Path;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathBuilder {

    public static final List<IPath> BUILT_PATHS = new ArrayList<>();

    public static PathBuilder create(ResourceLocation registryName) {
        return new PathBuilder(registryName, Path::new);
    }

    private final ResourceLocation registryName;
    private final Function4<IFormattableTextComponent, IFormattableTextComponent, Integer, PathFeatures, IPath> factory;
    private NonNullSupplier<IFormattableTextComponent> name;
    private NonNullSupplier<IFormattableTextComponent> description;
    private NonNullSupplier<Integer> maxLevel = () -> 1;
    private final Int2ObjectArrayMap<List<IPathFeature>> pathFeatures;

    private PathBuilder(ResourceLocation registryName, Function4<IFormattableTextComponent, IFormattableTextComponent, Integer, PathFeatures, IPath> factory) {
        this.registryName = registryName;
        this.factory = factory;
        this.pathFeatures = new Int2ObjectArrayMap<>();
    }

    public PathBuilder name(NonNullSupplier<IFormattableTextComponent> name) {
        this.name = name;
        return this;
    }

    public PathBuilder description(NonNullSupplier<IFormattableTextComponent> description) {
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
        path.setRegistryName(this.registryName);
        BUILT_PATHS.add(path);
        return path;
    }

}

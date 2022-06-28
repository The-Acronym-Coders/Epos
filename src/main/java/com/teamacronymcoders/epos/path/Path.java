package com.teamacronymcoders.epos.path;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.path.PathInfo;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.registry.PathRegistrar;
import com.teamacronymcoders.epos.util.EposRegistries;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.ashwork.dynamicregistries.entry.DynamicEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cod;
import org.jetbrains.annotations.NotNull;

public class Path implements IPath {

    private final NonNullSupplier<MutableComponent> name;
    private final NonNullSupplier<MutableComponent> description;
    private final NonNullSupplier<Integer> maxLevel;
    private final NonNullSupplier<PathFeatures> pathFeatures;
    private final NonNullSupplier<Codec<? extends IPath>> pathCodec;


    private String translationKey;

    /**
     * @param name
     * @param description
     * @param maxLevel
     * @param pathFeatures
     */
    public Path(MutableComponent name, MutableComponent description, int maxLevel, PathFeatures pathFeatures) {
        this.name = () -> name;
        this.description = () -> description;
        this.maxLevel = () -> maxLevel;
        this.pathFeatures = () -> pathFeatures;
        this.pathCodec = PathRegistrar.PATH_SERIALIZER;
    }

    /**
     * @param name
     * @param description
     * @param maxLevel
     * @param pathFeatures
     */
    public Path(MutableComponent name, MutableComponent description, int maxLevel, PathFeatures pathFeatures, Codec<? extends IPath> pathCodec) {
        this.name = () -> name;
        this.description = () -> description;
        this.maxLevel = () -> maxLevel;
        this.pathFeatures = () -> pathFeatures;
        if (pathCodec == null) {
            this.pathCodec = PathRegistrar.PATH_SERIALIZER;
        } else {
            this.pathCodec = () -> pathCodec;
        }

    }

    /**
     * @param name
     * @param description
     * @param maxLevel
     * @param pathFeatures
     * @param pathCodec
     */
    public Path(NonNullSupplier<MutableComponent> name, NonNullSupplier<MutableComponent> description, NonNullSupplier<Integer> maxLevel, NonNullSupplier<PathFeatures> pathFeatures, NonNullSupplier<Codec<? extends IPath>> pathCodec) {
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.pathFeatures = pathFeatures;
        this.pathCodec = pathCodec;
    }

    @Override
    public MutableComponent getName() {
        return this.name.get();
    }

    @Override
    public MutableComponent getDescription() {
        return this.description.get();
    }

    @Override
    public boolean isHidden(LivingEntity character, ICharacterSheet stats) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel.get();
    }

    @Override
    public PathFeatures getPathFeatures() {
        return this.pathFeatures.get();
    }

    @Override
    public void addLevel(LivingEntity character, ICharacterSheet stats, int levelsToAdd) {
        PathInfo info = stats.getPaths().getOrCreate(EposRegistries.PATHS.get().getKey(this));
        int currentLevel = info.getLevel();
        info.setLevel(Math.min(currentLevel + levelsToAdd, getMaxLevel()));
    }

    @Override
    public void removeLevel(LivingEntity character, ICharacterSheet stats, int levelsToRemove) {
        PathInfo info = stats.getPaths().getOrCreate(EposRegistries.PATHS.get().getKey(this));
        int currentLevel = info.getLevel();
        info.setLevel(Math.max(currentLevel + levelsToRemove, 0));
    }

    @Override
    public @NotNull String getTranslationKey(ResourceLocation id) {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("path", id);
        }
        return this.translationKey;
    }

    @Override
    public Codec<? extends IPath> codec() {
        return this.pathCodec.get();
    }


}

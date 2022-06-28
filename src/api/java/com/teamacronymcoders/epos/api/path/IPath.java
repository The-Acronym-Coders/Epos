package com.teamacronymcoders.epos.api.path;

import com.mojang.serialization.Codec;
import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.features.PathFeatures;
import com.teamacronymcoders.epos.path.Path;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// TODO: Document Main Interface Object
public interface IPath extends IDescribable {

    /**
     * Codec for (de)serializing biome modifiers inline.
     * Mods can use this for data generation.
     */
    Codec<IPath> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> EposRegistries.PATH_SERIALIZERS.get().getCodec())
            .dispatch(IPath::codec, Function.identity());

    /**
     * Indicates if the Path is 'Hidden' from the Player in the Path GUI.
     *
     * @param character The {@link LivingEntity} Character.
     * @param stats     The {@link LivingEntity} 's Character Stats.
     * @return Returns if the {@link IPath} is visible for the Player in the Path GUI.
     */
    boolean isHidden(LivingEntity character, ICharacterSheet stats);

    /**
     * Returns the max level of the {@link IPath} as an integer.
     *
     * @return Returns what the 'Max' level of the {@link IPath} is.
     */
    int getMaxLevel();

    /**
     * @return Returns the {@link PathFeatures} for the specific {@link Path}.
     */
    PathFeatures getPathFeatures();

    /**
     * Adds a certain amount of levels to the {@link IPath} of the {@link LivingEntity} Character.
     *
     * @param character   The {@link LivingEntity} Character.
     * @param stats       The {@link LivingEntity} 's {@link ICharacterSheet}.
     * @param levelsToAdd The amount of levels to add.
     */
    void addLevel(LivingEntity character, ICharacterSheet stats, int levelsToAdd);

    /**
     * Removes a certain amount of levels to the {@link IPath} of the {@link LivingEntity} Character.
     *
     * @param character      The {@link LivingEntity} Character.
     * @param stats          The {@link LivingEntity} 's {@link ICharacterSheet}.
     * @param levelsToRemove The amount of levels to remove.
     */
    void removeLevel(LivingEntity character, ICharacterSheet stats, int levelsToRemove);

    @NotNull String getTranslationKey(ResourceLocation id);

    Codec<? extends IPath> codec();
}

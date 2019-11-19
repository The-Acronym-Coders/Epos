package com.teamacronymcoders.epos.api.requirements;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import java.util.function.BiPredicate;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public interface IRequirement extends BiPredicate<LivingEntity, ICharacterStats> {

    /**
     * Gets the tooltip for this requirement to show in various places like GUIs.
     *
     * @param matches True if the requirement is met or false if it is not met.
     *
     * @return An {@link ITextComponent} to display for this requirement.
     *
     * @implNote It is expected to support both values of matches so that it is easy for players to determine if they meet a requirement or not.
     * @apiNote This method will also be used by things like "not" requirements.
     */
    @Nonnull
    ITextComponent getToolTip(boolean matches);

    /**
     * Checks if this requirement is met by the given {@link LivingEntity} who has the given {@link ICharacterStats}
     *
     * @param entity The entity to check.
     * @param stats  The {@link ICharacterStats} of the entity.
     *
     * @return True if the entity meets this requirement, false otherwise.
     */
    boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats);

    default boolean test(LivingEntity entity, ICharacterStats stats) {
        if (entity == null || stats == null) {
            return false;
        }
        return isMet(entity, stats);
    }

    /**
     * Compares a given {@link IRequirement} against this one and returns a {@link RequirementComparision} of the two requirements.
     *
     * @param other The {@link  IRequirement} to compare.
     *
     * @return The {@link RequirementComparision} of the given {@link IRequirement} and this one.
     */
    @Nonnull
    RequirementComparision compare(IRequirement other);
    //TODO: Should we have them override hashCode/equals for if the two requirements are identical?

    //TODO: Maybe make an interface for ICombinableRequirement:
    // combine(ICombinableRequirement other, Type type), and require that they support "adding" and then optionally multiplying???

    //TODO: Add support for requirement caching.
}
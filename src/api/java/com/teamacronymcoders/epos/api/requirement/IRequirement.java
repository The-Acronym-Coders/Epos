package com.teamacronymcoders.epos.api.requirement;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import java.util.function.BiPredicate;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public interface IRequirement extends BiPredicate<LivingEntity, ICharacterStats> {

    //TODO: Do we need some other form of check, because for logic requirements if we want to show individual branches as
    // being "met" we do not have enough information from a boolean of if it is met overall
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
     * Compares a given {@link IRequirement} against this one and returns a {@link RequirementComparison} of the two requirements.
     *
     * @param other The {@link  IRequirement} to compare.
     *
     * @return The {@link RequirementComparison} of the given {@link IRequirement} and this one.
     *
     * @implNote While the default implementations do check {@link #canCompareWith(IRequirement)}, it still makes sense to verify that the type is proper when
     * implementing this method, as the spec does not require checking {@link #canCompareWith(IRequirement)} given that is mainly a helper method to allow quick failing
     * if neither {@link IRequirement} knows how to compare to the other.
     */
    @Nonnull
    RequirementComparison compare(IRequirement other);

    /**
     * A helper method to allow quickly checking if this {@link IRequirement} is comparable with another {@link IRequirement}. This is mainly used so that in cases where
     * there may be multiple requirements stored in a single requirement, such as with logic requirements, so then while this {@link IRequirement} may not know how to
     * compare itself to the logic requirement, we can quickly check the other direction and see that the logic requirement knows how to compare itself against us.
     *
     * @param other The {@link  IRequirement} to check if we know how to compare to.
     *
     * @return True if we can compare ourselves with the given {@link IRequirement}, false otherwise.
     *
     * @apiNote It is not required to check this method before calling {@link #compare(IRequirement)} but it is recommended.
     */
    boolean canCompareWith(IRequirement other);
    //TODO: Should we have them override hashCode/equals for if the two requirements are identical?

    //TODO: Maybe make an interface for ICombinableRequirement:
    // combine(ICombinableRequirement other, Type type), and require that they support "adding" and then optionally multiplying???

    //TODO: Add support for requirement caching.
}
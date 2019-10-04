package com.teamacronymcoders.epos.api.requirements;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public interface IRequirement {

    /**
     * Gets the tooltip for this requirement to show in various places like GUIs.
     * @param matches True if the requirement is met or false if it is not met.
     * @return An {@link ITextComponent} to display for this requirement.
     * @implNote It is expected to support both values of matches so that it is
     * easy for players to determine if they meet a requirement or not.
     * @apiNote This method will also be used by things like "not" requirements.
     */
    @Nonnull
    ITextComponent getToolTip(boolean matches);

    /**
     * Checks if this requirement is met by the given {@link LivingEntity} who has the
     * given {@link ICharacterStats}
     * @param entity The entity to check.
     * @param stats The {@link ICharacterStats} of the entity.
     * @return True if the entity meets this requirement, false otherwise.
     */
    boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats);

    //TODO: Requirement comparision, Allow for "extended" data so that things like
    // skills|level in reskillable would then say "same type", and then give the number
    // Maybe have it be another interface that is for supporting extended compare

    //TODO: Add support for requirement caching.
}
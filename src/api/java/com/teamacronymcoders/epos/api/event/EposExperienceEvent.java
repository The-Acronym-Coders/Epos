package com.teamacronymcoders.epos.api.event;

import com.teamacronymcoders.epos.api.capability.EposCapabilities;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.event.enums.EposEventType;
import com.teamacronymcoders.epos.path.Path;
import com.teamacronymcoders.epos.skill.Skill;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The core event for experience-based interactions.
 * Currently the main event(s) are:
 * - {@link EposExperienceEvent.GainExperience}
 *
 * More events may be added later for example for dealing with losing experience.
 */
public class EposExperienceEvent extends LivingEvent {
    private final LazyOptional<ICharacterSheet> sheet;
    @Nonnull
    private final EposEventType type;
    @Nullable
    private final ResourceLocation typeObjectId;

    public EposExperienceEvent(LivingEntity entity, @Nonnull EposEventType type, @Nullable ResourceLocation typeObjectId) {
        super(entity);
        this.sheet = entity.getCapability(EposCapabilities.CHARACTER_SHEET);
        this.type = type;
        this.typeObjectId = typeObjectId;
    }

    /**
     * @return Returns the {@link LazyOptional<ICharacterSheet>} of the {@link ICharacterSheet} Capability.
     */
    public LazyOptional<ICharacterSheet> getSheet() {
        return sheet;
    }

    /**
     * Returns the type of experienced gained, which will be either {@link EposEventType#CHARACTER}, {@link EposEventType#PATH} or {@link EposEventType#SKILL}
     * @return Returns what type of experience was gained.
     */
    @Nonnull
    public EposEventType getType() {
        return type;
    }

    /**
     * Returns the {@link ResourceLocation} id of the type object gaining the experience.
     * @return Returns the {@link ResourceLocation} id of the type object.
     */
    @Nullable
    public ResourceLocation getTypeObjectId() {
        return typeObjectId;
    }

    /**
     * The {@link EposExperienceEvent.GainExperience} event used for when an Character is gaining either {@link com.teamacronymcoders.epos.api.character.CharacterSheet}, {@link Path} or {@link Skill} experience.
     * This event is also a {@link Cancelable} event, which means that you can cancel it to disallow the "Character" gaining experience.
     */
    @Cancelable
    public static class GainExperience extends EposExperienceEvent {
        private int experienceAmount;

        /**
         * Constructor for the main {@link GainExperience} event.
         * @param entity The "Character" {@link LivingEntity}.
         * @param experienceAmount The amount of experience being gained.
         */
        public GainExperience(LivingEntity entity, EposEventType type, ResourceLocation typeObjectId, int experienceAmount) {
            super(entity, type, typeObjectId);
            this.experienceAmount = experienceAmount;
        }

        /**
         * @return Returns the Experience Amount being gained.
         */
        public int getExperienceAmount() {
            return experienceAmount;
        }

        /**
         * Sets the current experience amount to X value.
         * @param experienceAmount The experience amount to override with.
         */
        public void setExperienceAmount(int experienceAmount) {
            this.experienceAmount = experienceAmount;
        }

        /**
         * Increments the current experience amount by X value.
         * @param increment The experience amount to increment the current amount by.
         */
        public void increaseExperienceAmount(int increment) {
            this.experienceAmount += increment;
        }

        /**
         * Decrements the current experience amount by X value, with a {@link Math#max(int, int)} check so it never goes below 0.
         * @param decrement The experience amount to decrement the current amount by.
         */
        public void decreaseExperienceAmount(int decrement) {
            this.experienceAmount = Math.max(this.experienceAmount - decrement, 0);
        }
    }
}

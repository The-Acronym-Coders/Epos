package com.teamacronymcoders.epos.api.event;

import com.teamacronymcoders.epos.api.capability.EposCapabilities;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.enums.EposEventType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The core event for level-based interactions.
 * Currently the main event(s) are:
 * - {@link EposLevelEvent.LevelUpEvent}
 * - {@link EposLevelEvent.LevelDownEvent}
 * <p>
 * More events may be added later.
 */
public class EposLevelEvent extends LivingEvent {
    private final LazyOptional<ICharacterSheet> sheet;
    @Nonnull
    private final EposEventType type;
    @Nullable
    private final ResourceLocation typeObjectId;
    private final int oldLevel;
    private final int newLevel;

    public EposLevelEvent(LivingEntity entity, @Nonnull EposEventType type, @Nullable ResourceLocation typeObjectId, int oldLevel, int newLevel) {
        super(entity);
        this.sheet = entity.getCapability(EposCapabilities.CHARACTER_SHEET);
        this.type = type;
        this.typeObjectId = typeObjectId;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    /**
     * @return Returns the {@link LazyOptional<ICharacterSheet>} of the {@link ICharacterSheet} Capability.
     */
    public LazyOptional<ICharacterSheet> getSheet() {
        return sheet;
    }

    /**
     * Returns the type of experienced gained, which will be either {@link EposEventType#CHARACTER}, {@link EposEventType#PATH} or {@link EposEventType#SKILL}
     *
     * @return Returns what type of experience was gained.
     */
    @Nonnull
    public EposEventType getType() {
        return type;
    }

    /**
     * Returns the {@link ResourceLocation} id of the type object gaining the experience.
     *
     * @return Returns the {@link ResourceLocation} id of the type object.
     */
    @Nullable
    public ResourceLocation getTypeObjectId() {
        return typeObjectId;
    }

    /**
     * Returns the old level.
     *
     * @return Returns the level pre-change.
     */
    public int getOldLevel() {
        return oldLevel;
    }

    /**
     * Returns the new level.
     *
     * @return Returns the level post-change.
     */
    public int getNewLevel() {
        return newLevel;
    }

    /**
     * The {@link EposLevelEvent.LevelUpEvent} event used for when a Character is leveling-up either as a Character, or leveling-up an {@link com.teamacronymcoders.epos.path.Path} or an {@link com.teamacronymcoders.epos.skill.Skill}.
     * This event is also a {@link Cancelable} event, which means that you can cancel it to disallow the "Character" leveling-up.
     */
    @Cancelable
    public static class LevelUpEvent extends EposLevelEvent {
        public LevelUpEvent(LivingEntity entity, EposEventType type, ResourceLocation typeObjectId, int oldLevel, int newLevel) {
            super(entity, type, typeObjectId, oldLevel, newLevel);
        }
    }

    /**
     * The {@link EposLevelEvent.LevelDownEvent} event used for when a Character is leveling-down either as a Character, or leveling-down an {@link com.teamacronymcoders.epos.path.Path} or an {@link com.teamacronymcoders.epos.skill.Skill}.
     * This event is also a {@link Cancelable} event, which means that you can cancel it to disallow the "Character" leveling-down.
     */
    @Cancelable
    public static class LevelDownEvent extends EposLevelEvent {
        public LevelDownEvent(LivingEntity entity, EposEventType type, ResourceLocation typeObjectId, int oldLevel, int newLevel) {
            super(entity, type, typeObjectId, oldLevel, newLevel);
        }
    }
}

package com.teamacronymcoders.epos.api.event;

import com.teamacronymcoders.epos.api.enums.EposEventType;
import com.teamacronymcoders.epos.api.enums.EposGrantOperator;
import com.teamacronymcoders.epos.api.enums.EposGrantType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * The core event for level-based interactions.
 * Currently the main event(s) are:
 * - {@link EposGrantEvent.Path}
 * - {@link EposGrantEvent.Skill}
 * - {@link EposGrantEvent.Feat}
 * <p>
 * More events may be added later.
 */
public class EposGrantEvent extends LivingEvent {
    private final EposGrantType type;
    private final EposGrantOperator operator;
    private final ResourceLocation typeObjectId;

    public EposGrantEvent(LivingEntity entity, EposGrantType type, EposGrantOperator operator, ResourceLocation typeObjectId) {
        super(entity);
        this.type = type;
        this.operator = operator;
        this.typeObjectId = typeObjectId;
    }

    /**
     * Returns the type of experienced gained, which will be either {@link EposEventType#CHARACTER}, {@link EposEventType#PATH} or {@link EposEventType#SKILL}
     *
     * @return Returns what type of experience was gained.
     */
    public EposGrantType getType() {
        return type;
    }

    /**
     * Returns the Operator setting of either {@link EposGrantOperator#UNLOCK} or {@link EposGrantOperator#LOCK} which dictates if the event is used to unlock or lock.
     *
     * @return Returns the {@link EposGrantOperator} type for the Event.
     */
    public EposGrantOperator getOperator() {
        return operator;
    }

    /**
     * Returns the {@link ResourceLocation} id of the type object gaining the experience.
     *
     * @return Returns the {@link ResourceLocation} id of the type object.
     */
    public ResourceLocation getTypeObjectId() {
        return typeObjectId;
    }

    /**
     * The {@link EposGrantEvent.Path} event is fired when Unlocking or Locking a {@link com.teamacronymcoders.epos.path.Path} on the Character.
     * This event is also a {@link Cancelable} event, which means that you can cancel it to disallow the "Character" unlocking or locking a specific {@link com.teamacronymcoders.epos.path.Path}.
     */
    @Cancelable
    public static class Path extends EposGrantEvent {
        public Path(LivingEntity entity, EposGrantOperator operator, ResourceLocation typeObjectId) {
            super(entity, EposGrantType.PATH, operator, typeObjectId);
        }
    }

    /**
     * The {@link EposGrantEvent.Skill} event is fired when Unlocking or Locking a {@link com.teamacronymcoders.epos.skill.Skill} on the Character.
     * This event is also a {@link Cancelable} event, which means that you can cancel it to disallow the "Character" unlocking or locking a specific {@link com.teamacronymcoders.epos.skill.Skill}.
     */
    @Cancelable
    public static class Skill extends EposGrantEvent {
        public Skill(LivingEntity entity, EposGrantOperator operator, ResourceLocation typeObjectId) {
            super(entity, EposGrantType.SKILL, operator, typeObjectId);
        }
    }

    /**
     * The {@link EposGrantEvent.Feat} event is fired when Unlocking or Locking a {@link com.teamacronymcoders.epos.feat.Feat} on the Character.
     * This event is also a {@link Cancelable} event, which means that you can cancel it to disallow the "Character" unlocking or locking a specific {@link com.teamacronymcoders.epos.feat.Feat}.
     */
    @Cancelable
    public static class Feat extends EposGrantEvent {
        public Feat(LivingEntity entity, EposGrantOperator operator, ResourceLocation typeObjectId) {
            super(entity, EposGrantType.FEAT, operator, typeObjectId);
        }
    }
}

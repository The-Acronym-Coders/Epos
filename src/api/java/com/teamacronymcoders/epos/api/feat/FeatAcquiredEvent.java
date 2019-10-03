package com.teamacronymcoders.epos.api.feat;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class FeatAcquiredEvent extends LivingEvent {
    private final Feat featAcquired;
    private final FeatSource featSource;
    private final ICharacterStats characterStats;

    protected FeatAcquiredEvent(LivingEntity character, Feat featAcquired, FeatSource featSource,
                                ICharacterStats characterStats) {
        super(character);
        this.featAcquired = featAcquired;
        this.featSource = featSource;
        this.characterStats = characterStats;
    }

    public Feat getFeatAcquired() {
        return featAcquired;
    }

    public ICharacterStats getCharacterStats() {
        return characterStats;
    }

    public FeatSource getFeatSource() {
        return featSource;
    }

    @Cancelable
    public static class Pre extends FeatAcquiredEvent {
        public Pre(LivingEntity entity, Feat featAcquired, FeatSource featSource, ICharacterStats characterStats) {
            super(entity, featAcquired, featSource, characterStats);
        }
    }

    public static class Post extends FeatAcquiredEvent {
        public Post(LivingEntity entity, Feat featAcquired, FeatSource featSource, ICharacterStats characterStats) {
            super(entity, featAcquired, featSource, characterStats);
        }
    }
}

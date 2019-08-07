package com.teamacronymcoders.eposmajorum.api.feat;

import com.teamacronymcoders.eposmajorum.api.characterstats.ICharacterStats;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class FeatAcquiredEvent extends LivingEvent {
    private final IFeat featAcquired;
    private final FeatSource featSource;
    private final ICharacterStats characterStats;

    protected FeatAcquiredEvent(LivingEntity character, IFeat featAcquired, FeatSource featSource,
                                ICharacterStats characterStats) {
        super(character);
        this.featAcquired = featAcquired;
        this.featSource = featSource;
        this.characterStats = characterStats;
    }

    public IFeat getFeatAcquired() {
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
        public Pre(LivingEntity entity, IFeat featAcquired, FeatSource featSource, ICharacterStats characterStats) {
            super(entity, featAcquired, featSource, characterStats);
        }
    }

    public static class Post extends FeatAcquiredEvent {
        public Post(LivingEntity entity, IFeat featAcquired, FeatSource featSource, ICharacterStats characterStats) {
            super(entity, featAcquired, featSource, characterStats);
        }
    }
}

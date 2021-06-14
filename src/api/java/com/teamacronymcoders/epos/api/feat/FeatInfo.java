package com.teamacronymcoders.epos.api.feat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 *
 */
public class FeatInfo {

    public static final Codec<FeatInfo> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Codec.BOOL.fieldOf("isUnlocked").forGetter(FeatInfo::isUnlocked),
            Codec.BOOL.fieldOf("isAbility").forGetter(FeatInfo::isAbility))
        .apply(instance, FeatInfo::new)
    );

    private boolean isUnlocked;
    private boolean isAbility;

    /**
     * Default Constructor
     */
    public FeatInfo() {
        this.isUnlocked = false;
        this.isAbility = false;
    }

    /**
     * Codec Constructor
     * @param isUnlocked Whether the Path is Unlocked or Not.
     * @param isAbility Whether the Path is an Active ability or Not.
     */
    public FeatInfo(boolean isUnlocked, boolean isAbility) {
        this.isUnlocked = isUnlocked;
        this.isAbility = isAbility;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public boolean isAbility() {
        return isAbility;
    }

    public void setAbility(boolean ability) {
        isAbility = ability;
    }
}

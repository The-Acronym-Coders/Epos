package com.teamacronymcoders.epos.api.feat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundNBT;

/**
 *
 */
public class FeatInfo {

    public static final Codec<FeatInfo> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.BOOL.fieldOf("isUnlocked").forGetter(FeatInfo::isUnlocked))
            .apply(instance, FeatInfo::new)
    );

    private boolean isUnlocked;

    /**
     * Default Constructor
     */
    public FeatInfo() {
        this.isUnlocked = false;
    }

    /**
     * Codec Constructor
     *
     * @param isUnlocked Whether the Path is Unlocked or Not.
     */
    public FeatInfo(boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

}

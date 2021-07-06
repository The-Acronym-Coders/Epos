package com.teamacronymcoders.epos.api.path;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;


/**
 *
 */
public class PathInfo {

    public static final Codec<PathInfo> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.intRange(0, 99).optionalFieldOf("level", 0).forGetter(PathInfo::getLevel),
                    Codec.BOOL.fieldOf("isUnlocked").forGetter(PathInfo::isUnlocked))
            .apply(instance, PathInfo::new)
    );

    private int level;
    private boolean isUnlocked;

    /**
     * Default Constructor
     */
    public PathInfo() {
        this.level = 0;
        this.isUnlocked = false;
    }

    /**
     * Codec Constructor
     *
     * @param level      The Current Level.
     * @param isUnlocked Whether the Path is Unlocked or Not.
     */
    public PathInfo(int level, boolean isUnlocked) {
        this.level = level;
        this.isUnlocked = isUnlocked;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }
}

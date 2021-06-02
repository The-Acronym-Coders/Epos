package com.teamacronymcoders.epos.locks.key.harvest;

import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;

public abstract class HarvestLockKey implements IFuzzyLockKey {

    protected final int harvestLevel;

    protected HarvestLockKey(int harvestLevel) {
        if (harvestLevel < 0) {
            throw new IllegalArgumentException("Harvest level must be at least zero.");
        }
        this.harvestLevel = harvestLevel;
    }
}
package com.teamacronymcoders.epos.locks.keys.harvest;

import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;

public abstract class HarvestLockKey implements IFuzzyLockKey {

    protected final int harvestLevel;

    protected HarvestLockKey(int harvestLevel) {
        if (harvestLevel < 0) {
            //TODO: Add whatever information is needed here for a better error message
            throw new IllegalArgumentException();
        }
        this.harvestLevel = harvestLevel;
    }

    @Override
    public boolean isNotFuzzy() {
        return false;
    }
}
package com.teamacronymcoders.epos.locks.keys.harvest;

import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;

public abstract class HarvestLockKey implements IFuzzyLockKey {

    protected final int harvestLevel;

    protected HarvestLockKey(int harvestLevel) {
        if (harvestLevel < 0) {
            throw new IllegalArgumentException("Harvest level must be greater than or equal to zero. Received: '" + harvestLevel + "'.");
        }
        this.harvestLevel = harvestLevel;
    }

    @Override
    public boolean isNotFuzzy() {
        return false;
    }
}
package com.teamacronymcoders.epos.locks.key.harvest;

import com.teamacronymcoders.epos.api.locks.key.IFuzzyLockKey;

public abstract class HarvestLockKey implements IFuzzyLockKey {

    protected final int harvestLevel;

    /**
     * @apiNote Ensure that the given harvest level is positive.
     */
    protected HarvestLockKey(int harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    @Override
    public boolean isNotFuzzy() {
        return false;
    }
}
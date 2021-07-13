package com.teamacronymcoders.epos.impl.featinfo;

import com.teamacronymcoders.epos.api.feat.info.FeatInfo;

public class EmbraceOfTheLotusFeatInfo extends FeatInfo {

    private int remainingTime = 0;

    public int getRemainingTime() {
        return remainingTime;
    }

    public void decrementRemainingTime() {
        this.remainingTime--;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}

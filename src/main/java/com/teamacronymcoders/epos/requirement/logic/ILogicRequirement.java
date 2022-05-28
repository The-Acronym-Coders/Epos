package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.requirement.IRequirement;
import javax.annotation.Nonnull;

public interface ILogicRequirement extends IRequirement {

    @Nonnull
    IRequirement simplify();

    int getDepth();
}
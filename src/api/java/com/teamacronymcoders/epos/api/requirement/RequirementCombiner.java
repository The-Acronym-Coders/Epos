package com.teamacronymcoders.epos.api.requirement;

//TODO: Rename this so it is clearer that this is just a "Default" simple implementation
public class RequirementCombiner implements IRequirementCombiner {

    //TODO: Theoretically we could and *should* flatten the requirements so that
    // if we have an outermost "and" requirement in the list of requirements
    // we should split it so that it is no longer an "and" requirement.
    // Could this maybe be used as a sort of proof of concept of the system for
    // making it so that it can "apply" multiple combiner types, as strictly speaking
    // the API should have no concept of an "and" requirement.
    // An alternate option would be to put the default requirement combiner implementation
    // inside of Epos instead of the API. (This may make more sense).
}
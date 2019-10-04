package com.teamacronymcoders.epos.api.requirements;

//TODO: Look into if this can/should use an existing system.
// Requirements could just be a forge registry, and then we have a class
// that helps manage them and interact with them.
public class RequirementRegistry {

    //TODO: We will need serializers to get requirements from JSON and also for sending
    // over the network
    // NOTE: We want to make sure that the impl for the serializers of logic requirements
    // do a few checks to try and simplify things similar to the LogicParser class in
    // Reskillable. We should also make sure that if it gets simplified it prints out
    // a message to the log to inform the pack dev that they can simplify their requirement
}
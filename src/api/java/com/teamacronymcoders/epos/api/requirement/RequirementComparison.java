package com.teamacronymcoders.epos.api.requirement;

//TODO: Maybe come up with a better name for this, also maybe order the enum?
//TODO: Add a javadocs how while it isn't really probabilities in that it should be fully contained it can be helpful
// to think of the requirements as probabilities of which one is more likely to be true/false
//TODO: Decide if we should invert how more/less restrictive work
//TODO: Helper to compare two requirements than checks both ways
public enum RequirementComparison {
    /**
     * For use in requirement comparison when the requirement being tested is incompatible with the requirement doing the testing.
     */
    INCOMPATIBLE,//TODO: Should this be renamed to undefined?
    /**
     * For use in requirement comparison when the requirement being tested is of the same overall type as the requirement doing the testing but has a differing subtype.
     *
     * For example: if both requirements are skill requirements, but they are for different skills, then this would be returned.
     */
    TYPE_MATCHES,
    /**
     * For use in requirement comparison when the requirement being tested is identical to the requirement doing the testing.
     */
    IDENTICAL,//TODO: Should this be renamed to something like SAME_RESTRICTIVENESS or IDENTICAL_RESTRICTIVENESS
    /**
     * For use in requirement comparison when the requirement being tested is more restrictive than the requirement doing the testing.
     */
    MORE_RESTRICTIVE_THAN,
    /**
     * For use in requirement comparison when the requirement being tested is less restrictive than the requirement doing the testing.
     */
    LESS_RESTRICTIVE_THAN,
    //TODO: Document this and use it in more places than just simplification
    OPPOSITE
}
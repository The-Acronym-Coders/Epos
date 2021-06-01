package com.teamacronymcoders.epos.api.requirement;

//TODO: Maybe come up with a better name for this, also maybe order the enum?
public enum RequirementComparison {
    /**
     * For use in requirement comparison when the requirement being tested is incompatible with the requirement doing the testing.
     */
    INCOMPATIBLE,
    /**
     * For use in requirement comparison when the requirement being tested is of the same overall type as the requirement doing the testing but has a differing subtype.
     *
     * For example: if both requirements are skill requirements, but they are for different skills, then this would be returned.
     */
    TYPE_MATCHES,
    /**
     * For use in requirement comparison when the requirement being tested is identical to the requirement doing the testing.
     */
    IDENTICAL,
    /**
     * For use in requirement comparison when the requirement being tested is more restrictive than the requirement doing the testing.
     */
    MORE_RESTRICTIVE_THAN,
    /**
     * For use in requirement comparison when the requirement being tested is less restrictive than the requirement doing the testing.
     */
    LESS_RESTRICTIVE_THAN
}
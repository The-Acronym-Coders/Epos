package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.requirement.SimpleRequirement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test the simplifying logic requirements")
class SimplifyLogicRequirementTest {//TODO: Add a bunch more tests for other logic requirement types

    @Test
    @DisplayName("Test simplifying NOT requirements of true")
    void testSimplifyNotTrue() {
        Assertions.assertEquals(FalseRequirement.INSTANCE, NOTRequirement.simplifiedNot(TrueRequirement.INSTANCE));
    }

    @Test
    @DisplayName("Test simplifying NOT requirements of false")
    void testSimplifyNotFalse() {
        Assertions.assertEquals(TrueRequirement.INSTANCE, NOTRequirement.simplifiedNot(FalseRequirement.INSTANCE));
    }

    @Test
    @DisplayName("Test simplifying out NOTs from a NOT requirement of a NOT requirement")
    void testSimplifyDoubleNot() {
        IRequirement requirement = new SimpleRequirement("test");
        Assertions.assertEquals(requirement, NOTRequirement.simplifiedNot(new NOTRequirement(requirement)));
    }

    @Test
    @DisplayName("Test simplifying ")//TODO
    void testSimplifyAnd() {
        IRequirement requirement = new SimpleRequirement("test");
        Assertions.assertEquals(requirement, NOTRequirement.simplifiedNot(new NOTRequirement(requirement)));
    }
}
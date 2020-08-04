package com.teamacronymcoders.epos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test JUnit")
public class TestJUnit {
    @Test
    @DisplayName("Ensure JUnit is working properly")
    void test() {
        Assertions.assertTrue(true);
    }
}

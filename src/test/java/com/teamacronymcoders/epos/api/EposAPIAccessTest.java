package com.teamacronymcoders.epos.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test accessing various internal API definitions")
class EposAPIAccessTest {

    @Test
    @DisplayName("Test getting the Lock Registry")
    void testGetRadiationManager() {
        Assertions.assertNotNull(EposAPI.getLockRegistry());
    }
}
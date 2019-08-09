package com.teamacronymcoders.epos.utils.helpers;

import java.util.Random;

public class MathHelper {
    private static final Random RANDOM = new Random();
    public static int nextIntInclusive(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}

package com.teamacronymcoders.epos.util;

import java.util.Random;

public class EposUtil {

    public static boolean getRandomizedChance(float chance) {
        return new Random().nextFloat() < chance;
    }
}

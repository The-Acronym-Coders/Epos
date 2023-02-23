package com.teamacronymcoders.epos.util;

import java.util.concurrent.ThreadLocalRandom;

public class EposUtil {

    public static boolean getRandomizedChance(float chance) {
        return ThreadLocalRandom.current().nextFloat() < chance;
    }
}

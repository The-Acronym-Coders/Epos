package com.teamacronymcoders.eposmajorum.utils.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class EMConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final GeneralConfigs GENERAL_CONFIGS = new GeneralConfigs(BUILDER);
    public static final SoundConfigs SOUND_CONFIGS = new SoundConfigs(BUILDER);

    public static class GeneralConfigs {
        public ForgeConfigSpec.IntValue testInt;
        public ForgeConfigSpec.LongValue testLong;
        public ForgeConfigSpec.DoubleValue testDouble;
        public ForgeConfigSpec.BooleanValue testBoolean;

        public GeneralConfigs(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            testInt = builder.comment("This Is A Test Int").defineInRange("testInt", 64, 1, Integer.MAX_VALUE);
            testLong = builder.comment("This Is A Test Long").defineInRange("testLong", 512, 1, Long.MAX_VALUE);
            testDouble = builder.comment("This Is A Test Double").defineInRange("testDouble", 66.6D, 1d, Double.MAX_VALUE);
            testBoolean = builder.comment("This Is A Test Bool").define("testBool", true);
            builder.pop();
        }
    }

    public static class SoundConfigs {
        public ForgeConfigSpec.BooleanValue enableSounds;
        public ForgeConfigSpec.BooleanValue enableLevelUpSound;

        public SoundConfigs(ForgeConfigSpec.Builder builder) {
            builder.push("Sounds");
            enableSounds = builder.comment("Enable All Sounds?").define("enable all sounds?", true);
            enableLevelUpSound = builder.comment("Enable Level-Up Sound?").define("enable level-up sound?", true);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec build = BUILDER.build();
}

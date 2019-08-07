package com.teamacronymcoders.eposmajorum.utils.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class EMConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final GeneralConfigs GENERAL_CONFIGS = new GeneralConfigs(BUILDER);
    public static final SoundConfigs SOUND_CONFIGS = new SoundConfigs(BUILDER);

    public static class GeneralConfigs {

        public GeneralConfigs(ForgeConfigSpec.Builder builder) {
            builder.push("General");

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

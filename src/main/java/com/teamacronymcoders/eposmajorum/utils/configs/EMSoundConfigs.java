package com.teamacronymcoders.eposmajorum.utils.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class EMSoundConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final SoundConfigs SOUND_CONFIGS = new SoundConfigs(BUILDER);

    public static class SoundConfigs {
        public ForgeConfigSpec.BooleanValue enableSounds;
        public ForgeConfigSpec.BooleanValue enableLevelUpSound;

        public SoundConfigs(ForgeConfigSpec.Builder builder) {
            builder.push("sound configs");
            enableSounds = builder.comment("Enable All Sounds?").define("Enable all sounds?", true);
            enableLevelUpSound = builder.comment("Enable Level-Up Sound?").define("Enable level-up sound?", true);
            builder.pop();
        }
    }
    public static final ForgeConfigSpec spec = BUILDER.build();
}

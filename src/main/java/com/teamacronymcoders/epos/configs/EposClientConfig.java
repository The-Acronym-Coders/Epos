package com.teamacronymcoders.epos.configs;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Objects;

public class EposClientConfig {
    private static EposClientConfig instance;

    private final Sounds sounds;
    private final ForgeConfigSpec spec;

    private EposClientConfig(ForgeConfigSpec.Builder builder) {
        this.sounds = new Sounds(builder);
        this.spec = builder.build();
    }

    public static ForgeConfigSpec initialize() {
        EposClientConfig config = new EposClientConfig(new ForgeConfigSpec.Builder());
        instance = config;
        return config.getSpec();
    }

    public static EposClientConfig getInstance() {
        return Objects.requireNonNull(instance, "Called for Config before it's Initialization");
    }

    public static Sounds getSounds() {
        return instance.sounds;
    }

    public ForgeConfigSpec getSpec() {
        return spec;
    }

    public static class Sounds {
        public ForgeConfigSpec.BooleanValue enableLevelUpSound;

        public Sounds(ForgeConfigSpec.Builder builder) {
            builder.push("Sounds");
            enableLevelUpSound = builder.comment("Enable Level-Up Sound?").define("level up sound", true);
            builder.pop();
        }
    }
}

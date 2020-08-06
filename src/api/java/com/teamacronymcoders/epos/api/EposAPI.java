package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.characterstats.CharacterStats;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.path.Path;
import com.teamacronymcoders.epos.api.skill.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EposAPI {

    static {
        init();
    }

    public static final String ID = "epos";
    public static final Logger LOGGER = LogManager.getLogger(ID);
    public static IForgeRegistry<Path> PATH = RegistryManager.ACTIVE.getRegistry(Path.class);
    public static IForgeRegistry<Skill> SKILL = RegistryManager.ACTIVE.getRegistry(Skill.class);
    public static IForgeRegistry<Feat> FEAT = RegistryManager.ACTIVE.getRegistry(Feat.class);

    @CapabilityInject(CharacterStats.class)
    public static Capability<CharacterStats> CHARACTER_STATS;

    @SuppressWarnings("unchecked")
    private static void init() {
        makeRegistry("path", Path.class).create();
        makeRegistry("skill", Skill.class).create();
        makeRegistry("feat", Feat.class).create();
    }

    public static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(String name, Class<T> type) {
        return new RegistryBuilder<T>()
            .setName(new ResourceLocation(ID, name))
            .setIDRange(1, Integer.MAX_VALUE - 1)
            .disableSaving()
            .setType(type);
    }

}

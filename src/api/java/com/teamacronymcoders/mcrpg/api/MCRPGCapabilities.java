package com.teamacronymcoders.mcrpg.api;

import com.teamacronymcoders.mcrpg.api.characterstats.ICharacterStats;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class MCRPGCapabilities {
    @CapabilityInject(ICharacterStats.class)
    public static Capability<ICharacterStats> CHARACTER_STATS;
}

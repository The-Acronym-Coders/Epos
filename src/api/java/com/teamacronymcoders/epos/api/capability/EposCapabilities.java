package com.teamacronymcoders.epos.api.capability;

import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class EposCapabilities {

    public static final ResourceLocation SHEET_ID = new ResourceLocation("epos", "character_sheet");

    public static Capability<ICharacterSheet> CHARACTER_SHEET = CapabilityManager.get(new CapabilityToken<>() {});

}

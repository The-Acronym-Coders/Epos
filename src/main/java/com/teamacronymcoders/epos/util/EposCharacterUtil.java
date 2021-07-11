package com.teamacronymcoders.epos.util;

import com.teamacronymcoders.epos.api.capability.EposCapabilities;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class EposCharacterUtil {

    public static LazyOptional<ICharacterSheet> getCharacterSheetOptional(LivingEntity character) {
        return character.getCapability(EposCapabilities.CHARACTER_SHEET);
    }

    @Nullable
    public static ICharacterSheet getCharacterSheet(LivingEntity character) {
        LazyOptional<ICharacterSheet> sheetOptional = getCharacterSheetOptional(character);
        if (sheetOptional.isPresent()) {
            return sheetOptional.resolve().orElse(null);
        }
        return null;
    }

    public static boolean hasPath(LivingEntity character, ResourceLocation pathId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getPaths().getOrCreate(pathId).getLevel() > 0;
        return false;
    }

    public static boolean hasSkill(LivingEntity character, ResourceLocation skillId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getSkills().getOrCreate(skillId).getLevel() > 0;
        return false;
    }

    public static boolean hasFeat(LivingEntity character, ResourceLocation featId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getFeats().getOrCreate(featId).isUnlocked();
        return false;
    }

}

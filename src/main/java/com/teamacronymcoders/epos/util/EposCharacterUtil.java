package com.teamacronymcoders.epos.util;

import com.teamacronymcoders.epos.api.capability.EposCapabilities;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.feat.info.FeatInfo;
import com.teamacronymcoders.epos.api.path.PathInfo;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.Arrays;

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

    public static int getSkillLevel(LivingEntity character, ResourceLocation skillId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getSkills().getOrCreate(skillId).getLevel();
        return 0;
    }

    public static boolean hasFeat(LivingEntity character, ResourceLocation featId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getFeats().getOrCreate(featId).isUnlocked();
        return false;
    }

    public static boolean hasFeats(LivingEntity character, ResourceLocation... featIds) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return Arrays.stream(featIds).allMatch(id -> sheet.getFeats().getOrCreate(id).isUnlocked());
        return false;
    }

    public static boolean anyFeatsAcquired(LivingEntity character, ResourceLocation... featIds) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return Arrays.stream(featIds).anyMatch(id -> sheet.getFeats().getOrCreate(id).isUnlocked());
        return false;
    }

    @Nullable
    public static PathInfo getPathInfo(LivingEntity character, ResourceLocation pathId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getPaths().getOrCreate(pathId);
        return null;
    }

    @Nullable
    public static SkillInfo getSkillInfo(LivingEntity character, ResourceLocation skillId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getSkills().getOrCreate(skillId);
        return null;
    }

    @Nullable
    public static FeatInfo getFeatInfo(LivingEntity character, ResourceLocation featId) {
        ICharacterSheet sheet = getCharacterSheet(character);
        if (sheet != null) return sheet.getFeats().getOrCreate(featId);
        return null;
    }

}

package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.EposConstants.NBT;
import com.teamacronymcoders.epos.api.characterstats.CharacterStats;
import com.teamacronymcoders.epos.api.characterstats.points.PointInfo;
import com.teamacronymcoders.epos.api.characterstats.points.PointType;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatInfo;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.feat.MissingFeat;
import com.teamacronymcoders.epos.api.path.MissingPath;
import com.teamacronymcoders.epos.api.path.Path;
import com.teamacronymcoders.epos.api.path.PathInfo;
import com.teamacronymcoders.epos.api.path.Paths;
import com.teamacronymcoders.epos.api.skill.MissingSkill;
import com.teamacronymcoders.epos.api.skill.Skill;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.util.Map;
import java.util.Map.Entry;

public class EposUtils {

    // CharacterStats
    public static CompoundNBT serializeCharacterStats(CharacterStats stats) {
        CompoundNBT characterNBT = new CompoundNBT();
        serializePoints(stats, characterNBT);
        serializeStorages(stats, characterNBT);
        return characterNBT;
    }

    public static void deserializeCharacterStats(CharacterStats stats, CompoundNBT nbt) {
        deserializePoints(stats, nbt);
        deserializeStorages(stats, nbt);
    }

    public static void serializePoints(CharacterStats stats, CompoundNBT nbt) {
        CompoundNBT points = new CompoundNBT();
        points.put(NBT.PATH, stats.getPointInfo(PointType.PATH).serializeNBT());
        points.put(NBT.SKILL, stats.getPointInfo(PointType.SKILL).serializeNBT());
        points.put(NBT.FEAT, stats.getPointInfo(PointType.FEAT).serializeNBT());
        nbt.put(NBT.POINTS, points);
    }

    public static void deserializePoints(CharacterStats stats, CompoundNBT nbt) {
        CompoundNBT points = nbt.getCompound(NBT.POINTS);
        stats.setPointInfo(PointInfo.createFromNBT(points.getCompound(NBT.PATH)), PointType.PATH);
        stats.setPointInfo(PointInfo.createFromNBT(points.getCompound(NBT.SKILL)), PointType.SKILL);
        stats.setPointInfo(PointInfo.createFromNBT(points.getCompound(NBT.FEAT)), PointType.FEAT);
    }

    public static void serializeStorages(CharacterStats stats, CompoundNBT nbt) {
        nbt.put(NBT.PATHS, stats.getPaths().serializeNBT());
        nbt.put(NBT.SKILLS, stats.getSkills().serializeNBT());
        nbt.put(NBT.FEATS, stats.getFeats().serializeNBT());
    }

    public static void deserializeStorages(CharacterStats stats, CompoundNBT nbt) {
        ListNBT paths = nbt.getList(NBT.PATHS, Constants.NBT.TAG_COMPOUND);
        ListNBT skills = nbt.getList(NBT.SKILLS, Constants.NBT.TAG_COMPOUND);
        ListNBT feats = nbt.getList(NBT.FEATS, Constants.NBT.TAG_COMPOUND);

        stats.setPaths(Paths.createFromNBT(paths));
        stats.setSkills(Skills.createFromNBT(skills));
        stats.setFeats(Feats.createFromNBT(feats));
    }



    // Paths Sub-Storage
    public static ListNBT serializePaths(Paths paths, ListNBT listNBT) {
        Map<ResourceLocation, PathInfo> pathInfoMap = paths.getPathInfoMap();
        for (Entry<ResourceLocation, PathInfo> entry : pathInfoMap.entrySet()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString(NBT.PATH_ID, entry.getKey().toString());
            nbt.put(NBT.PATH_INFO, entry.getValue().serializeNBT());
            listNBT.add(nbt);
        }
        return listNBT;
    }

    public static void deserializePaths(Paths paths, ListNBT listNBT) {
        Map<ResourceLocation, PathInfo> pathInfoMap = paths.getPathInfoMap();
        pathInfoMap.clear();
        for (int i = 0; i < listNBT.size(); i++) {
            CompoundNBT nbt = listNBT.getCompound(i);
            ResourceLocation pathRegistryName = new ResourceLocation(nbt.getString(NBT.PATH_ID));
            PathInfo pathInfo = deserializePathInfo(pathRegistryName, nbt.getCompound(NBT.PATH_INFO));
            pathInfoMap.put(pathRegistryName, pathInfo);
        }
    }

    public static PathInfo deserializePathInfo(ResourceLocation pathRegistryName, CompoundNBT nbt) {
        Path path = EposAPI.PATH.getValue(pathRegistryName);
        if (path == null) {
            PathInfo pathInfo = new PathInfo(new MissingPath(pathRegistryName));
            pathInfo.deserializeNBT(nbt);
            return pathInfo;
        }
        PathInfo pathInfo = new PathInfo(path);
        pathInfo.deserializeNBT(nbt);
        return pathInfo;
    }



    // Skills Sub-Storage
    public static ListNBT serializeSkills(Skills skills, ListNBT listNBT) {
        Map<ResourceLocation, SkillInfo> skillInfoMap = skills.getSkillInfoMap();
        for (Entry<ResourceLocation, SkillInfo> entry : skillInfoMap.entrySet()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString(NBT.SKILL_ID, entry.getKey().toString());
            nbt.put(NBT.SKILL_INFO, entry.getValue().serializeNBT());
            listNBT.add(nbt);
        }
        return listNBT;
    }

    public static void deserializeSkills(Skills skills, ListNBT listNBT) {
        Map<ResourceLocation, SkillInfo> skillInfoMap = skills.getSkillInfoMap();
        skillInfoMap.clear();
        for (int i = 0; i < listNBT.size(); i++) {
            CompoundNBT nbt = listNBT.getCompound(i);
            ResourceLocation skillRegistryName = new ResourceLocation(nbt.getString(NBT.SKILL_ID));
            SkillInfo skillInfo = deserializeSkillInfo(skillRegistryName, nbt.getCompound(NBT.SKILL_INFO));
            skillInfoMap.put(skillRegistryName, skillInfo);
        }
    }

    public static SkillInfo deserializeSkillInfo(ResourceLocation skillRegistryName, CompoundNBT nbt) {
        Skill skill = EposAPI.SKILL.getValue(skillRegistryName);
        if (skill == null) {
            SkillInfo skillInfo = new SkillInfo(new MissingSkill(skillRegistryName));
            skillInfo.deserializeNBT(nbt);
            return skillInfo;
        }
        SkillInfo skillInfo = new SkillInfo(skill);
        skillInfo.deserializeNBT(nbt);
        return skillInfo;
    }



    // Paths Sub-Storage
    public static ListNBT serializeFeats(Feats feats, ListNBT listNBT) {
        Map<ResourceLocation, FeatInfo> featInfoMap = feats.getFeatInfoMap();
        for (Entry<ResourceLocation, FeatInfo> entry : featInfoMap.entrySet()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString(NBT.FEAT_ID, entry.getKey().toString());
            nbt.put(NBT.FEAT_INFO, entry.getValue().serializeNBT());
            listNBT.add(nbt);
        }
        return listNBT;
    }

    public static void deserializeFeats(Feats feats, ListNBT listNBT) {
        Map<ResourceLocation, FeatInfo> featInfoMap = feats.getFeatInfoMap();
        featInfoMap.clear();
        for (int i = 0; i < listNBT.size(); i++) {
            CompoundNBT nbt = listNBT.getCompound(i);
            ResourceLocation pathRegistryName = new ResourceLocation(nbt.getString(NBT.FEAT_ID));
            FeatInfo pathInfo = deserializeFeatInfo(pathRegistryName, nbt.getCompound(NBT.FEAT_INFO));
            featInfoMap.put(pathRegistryName, pathInfo);
        }
    }

    public static FeatInfo deserializeFeatInfo(ResourceLocation featRegistryName, CompoundNBT nbt) {
        Feat feat = EposAPI.FEAT.getValue(featRegistryName);
        if (feat == null) {
            FeatInfo featInfo = new FeatInfo(new MissingFeat(featRegistryName));
            featInfo.deserializeNBT(nbt);
            return featInfo;
        }
        FeatInfo featInfo = new FeatInfo(feat);
        featInfo.deserializeNBT(nbt);
        return featInfo;
    }

}

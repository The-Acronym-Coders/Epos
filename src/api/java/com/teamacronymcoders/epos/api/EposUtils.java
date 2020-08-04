package com.teamacronymcoders.epos.api;

import com.teamacronymcoders.epos.api.EposConstants.NBT;
import com.teamacronymcoders.epos.api.characterstats.CharacterStats;
import com.teamacronymcoders.epos.api.characterstats.points.PointInfo;
import com.teamacronymcoders.epos.api.characterstats.points.PointType;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.Paths;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;

public class EposUtils {

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
        CompoundNBT paths = nbt.getCompound(NBT.PATHS);
        CompoundNBT skills = nbt.getCompound(NBT.SKILLS);
        CompoundNBT feats = nbt.getCompound(NBT.FEATS);

        stats.setPaths(Paths.createFromNBT(paths));
        stats.setSkills(Skills.createFromNBT(skills));
        stats.setFeats(Feats.createFromNBT(feats));
    }

}

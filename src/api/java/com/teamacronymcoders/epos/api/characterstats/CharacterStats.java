package com.teamacronymcoders.epos.api.characterstats;

import com.teamacronymcoders.epos.api.EposUtils;
import com.teamacronymcoders.epos.api.ability.Abilities;
import com.teamacronymcoders.epos.api.characterstats.points.PointInfo;
import com.teamacronymcoders.epos.api.characterstats.points.PointType;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.Paths;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;

public class CharacterStats implements ICharacterStats {
    private Paths paths;
    private Skills skills;
    private Feats feats;
    private Abilities abilities;

    private PointInfo pathPoints;
    private PointInfo skillPoints;
    private PointInfo featPoints;

    public CharacterStats() {
        this.paths = new Paths();
        this.skills = new Skills();
        this.feats = new Feats();
        this.abilities = new Abilities();

        // Player Starts with 1 of each point for the first level of choices.
        this.pathPoints = new PointInfo(PointType.PATH, 1, 1);
        this.skillPoints = new PointInfo(PointType.SKILL, 1, 1);
        this.featPoints = new PointInfo(PointType.FEAT, 1, 1);
    }

    // Used for Respawning Players/Entities
    public CharacterStats (CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    @Override
    public Paths getPaths() {
        return paths;
    }

    public void setPaths(Paths paths) {
        this.paths = paths;
    }

    @Override
    public Skills getSkills() {
        return skills;
    }

    @Override
    public Abilities getAbilities() {
        return null;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    @Override
    public Feats getFeats() {
        return feats;
    }

    public void setFeats(Feats feats) {
        this.feats = feats;
    }

    public PointInfo getPointInfo(PointType type) {
        switch (type) {
            case PATH: return pathPoints;
            case SKILL: return skillPoints;
            case FEAT: return featPoints;
        }
        return null;
    }

    public void setPointInfo(PointInfo info, PointType type) {
        switch (type) {
            case PATH: pathPoints = info;
            case SKILL: skillPoints = info;
            case FEAT: featPoints = info;
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        return EposUtils.serializeCharacterStats(this);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        EposUtils.deserializeCharacterStats(this, nbt);
    }
}

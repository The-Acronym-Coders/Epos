package com.teamacronymcoders.eposmajorum.characterstats;

import com.teamacronymcoders.eposmajorum.api.characterstats.ICharacterStats;
import com.teamacronymcoders.eposmajorum.api.feat.Feats;
import com.teamacronymcoders.eposmajorum.api.path.PathLevels;
import com.teamacronymcoders.eposmajorum.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;

public class CharacterStats implements ICharacterStats {
    private final PathLevels pathLevels;
    private final Feats feats;
    private final Skills skills;

    public CharacterStats() {
        this(new PathLevels(), new Feats(), new Skills());
    }

    public CharacterStats(PathLevels pathLevels, Feats feats, Skills skills) {
        this.pathLevels = pathLevels;
        this.feats = feats;
        this.skills = skills;
    }

    @Override
    public PathLevels getPathLevels() {
        return pathLevels;
    }

    @Override
    public Feats getFeats() {
        return feats;
    }

    @Override
    public Skills getSkills() {
        return skills;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT CompoundNBT = new CompoundNBT();
        CompoundNBT.put("path_levels", this.getPathLevels().serializeNBT());
        CompoundNBT.put("feats", this.getFeats().serializeNBT());
        CompoundNBT.put("skills", this.getSkills().serializeNBT());
        return CompoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.getPathLevels().deserializeNBT(nbt.getCompound("path_levels"));
        this.getFeats().deserializeNBT(nbt.getCompound("feats"));
        this.getSkills().deserializeNBT(nbt.getCompound("skills"));
    }
}

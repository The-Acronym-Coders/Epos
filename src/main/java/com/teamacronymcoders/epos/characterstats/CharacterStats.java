package com.teamacronymcoders.epos.characterstats;

import com.teamacronymcoders.epos.api.ability.Abilities;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.PathLevels;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

public class CharacterStats implements ICharacterStats {
    private final PathLevels pathLevels;
    private final Feats feats;
    private final Skills skills;
    private final Abilities abilities;

    public CharacterStats() {
        this(new PathLevels(), new Feats(), new Skills(), new Abilities());
    }

    public CharacterStats(PathLevels pathLevels, Feats feats, Skills skills, Abilities abilities) {
        this.pathLevels = pathLevels;
        this.feats = feats;
        this.skills = skills;
        this.abilities = abilities;
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
    public Abilities getAbilities() {
        return abilities;
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

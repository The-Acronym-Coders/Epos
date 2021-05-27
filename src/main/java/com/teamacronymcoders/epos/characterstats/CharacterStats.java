package com.teamacronymcoders.epos.characterstats;

import com.teamacronymcoders.epos.api.ability.Abilities;
import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.CharacterClassLevels;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;

public class CharacterStats implements ICharacterStats {
    private final CharacterClassLevels classLevels;
    private final Feats feats;
    private final Skills skills;
    private final Abilities abilities;

    public CharacterStats() {
        this(new CharacterClassLevels(), new Feats(), new Skills(), new Abilities());
    }

    public CharacterStats(CharacterClassLevels pathLevels, Feats feats, Skills skills, Abilities abilities) {
        this.classLevels = pathLevels;
        this.feats = feats;
        this.skills = skills;
        this.abilities = abilities;
    }

    @Override
    public CharacterClassLevels getClassLevels() {
        return classLevels;
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
        CompoundNBT.put("class_levels", this.getClassLevels().serializeNBT());
        CompoundNBT.put("feats", this.getFeats().serializeNBT());
        CompoundNBT.put("skills", this.getSkills().serializeNBT());
        return CompoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.getClassLevels().deserializeNBT(nbt.getCompound("class_levels"));
        this.getFeats().deserializeNBT(nbt.getCompound("feats"));
        this.getSkills().deserializeNBT(nbt.getCompound("skills"));
    }
}

package com.teamacronymcoders.epos.characterstats;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;

public class CharacterStats implements ICharacterStats {

    private final Skills skills;

    public CharacterStats() {
        this(new Skills());
    }

    public CharacterStats(Skills skills) {
        this.skills = skills;
    }

    @Override
    public Skills getSkills() {
        return skills;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT CompoundNBT = new CompoundNBT();
        CompoundNBT.put("skills", this.getSkills().serializeNBT());
        return CompoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.getSkills().deserializeNBT(nbt.getCompound("skills"));
    }
}

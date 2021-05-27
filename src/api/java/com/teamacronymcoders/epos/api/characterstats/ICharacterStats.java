package com.teamacronymcoders.epos.api.characterstats;

import com.teamacronymcoders.epos.api.ability.Abilities;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.CharacterClassLevels;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICharacterStats extends INBTSerializable<CompoundNBT> {
    // Stored Fields
    CharacterClassLevels getClassLevels();
    Feats getFeats();
    Skills getSkills();
    Abilities getAbilities();
}

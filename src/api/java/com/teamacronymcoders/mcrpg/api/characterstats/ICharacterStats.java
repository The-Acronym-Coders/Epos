package com.teamacronymcoders.mcrpg.api.characterstats;

import com.teamacronymcoders.mcrpg.api.feat.Feats;
import com.teamacronymcoders.mcrpg.api.path.PathLevels;
import com.teamacronymcoders.mcrpg.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICharacterStats extends INBTSerializable<CompoundNBT> {
    PathLevels getPathLevels();

    Feats getFeats();

    Skills getSkills();
}

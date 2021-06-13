package com.teamacronymcoders.epos.api.character;

import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.PathLevels;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

// TODO: Document Main Interface Object
public interface ICharacterStats extends INBTSerializable<CompoundNBT> {

    /**
     * Returns the serializable {@link PathLevels} 's object for the Character.
     * @return Returns the {@link PathLevels} object.
     */
    PathLevels getPathLevels();

    /**
     * Returns the serializable {@link Skills} 's object for the Character.
     * @return Returns the {@link Skills} object.
     */
    Skills getSkills();

    /**
     * Returns the serializable {@link Feats} 's object for the Character.
     * @return Returns the {@link Feats} object.
     */
    Feats getFeats();
}

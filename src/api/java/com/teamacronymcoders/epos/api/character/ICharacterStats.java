package com.teamacronymcoders.epos.api.character;

import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.Paths;
import com.teamacronymcoders.epos.api.skill.Skills;

// TODO: Document Main Interface Object
public interface ICharacterStats {

    /**
     * Returns the serializable {@link Paths} 's object for the Character.
     * @return Returns the {@link Paths} object.
     */
    Paths getPaths();

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

package com.teamacronymcoders.epos.feat.ability;

import com.teamacronymcoders.epos.api.feat.ability.IAbility;
import com.teamacronymcoders.epos.feat.Feat;
import net.minecraft.util.text.IFormattableTextComponent;

public class Ability extends Feat {

    /**
     * Constructor for Feat(s).
     *
     * @param name        The Name of the Feat.
     * @param description The Description of the Feat.
     * @param isUnlocked  If the Feat is Unlocked or Not.
     * @param isAbility   If the Feat is an Active 'Ability'.
     */
    public Ability(IFormattableTextComponent name, IFormattableTextComponent description, boolean isUnlocked, boolean isAbility) {
        super(name, description, isUnlocked, isAbility);
    }

}

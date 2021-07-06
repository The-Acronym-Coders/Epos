package com.teamacronymcoders.epos.feat.ability;

import com.teamacronymcoders.epos.feat.Feat;
import net.minecraft.util.text.IFormattableTextComponent;

public class Ability extends Feat {

    /**
     * Constructor for Feat(s).
     *
     * @param name        The Name of the Feat.
     * @param description The Description of the Feat.
     */
    public Ability(IFormattableTextComponent name, IFormattableTextComponent description) {
        super(name, description, true);
    }

}

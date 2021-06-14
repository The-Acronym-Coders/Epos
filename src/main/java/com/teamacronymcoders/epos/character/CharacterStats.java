package com.teamacronymcoders.epos.character;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterStats;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.Paths;
import com.teamacronymcoders.epos.api.skill.Skills;

/**
 *
 */
public class CharacterStats implements ICharacterStats {

    public static final Codec<CharacterStats> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Paths.CODEC.fieldOf("paths").forGetter(ICharacterStats::getPaths),
            Skills.CODEC.fieldOf("skills").forGetter(ICharacterStats::getSkills),
            Feats.CODEC.fieldOf("feats").forGetter(ICharacterStats::getFeats)
        ).apply(instance, CharacterStats::new)
    );

    private Paths paths;
    private Skills skills;
    private Feats feats;

    /**
     * Default Constructor
     */
    public CharacterStats() {
        this.paths = new Paths();
        this.skills = new Skills();
        this.feats = new Feats();
    }

    /**
     * Codec Constructor
     * @param paths The Character's Paths
     * @param skills The Character's Skills
     * @param feats The Character's Feats
     */
    public CharacterStats(Paths paths, Skills skills, Feats feats) {
        this.paths = paths;
        this.skills = skills;
        this.feats = feats;
    }


    @Override
    public Paths getPaths() {
        return this.paths;
    }

    @Override
    public Skills getSkills() {
        return this.skills;
    }

    @Override
    public Feats getFeats() {
        return this.feats;
    }

}

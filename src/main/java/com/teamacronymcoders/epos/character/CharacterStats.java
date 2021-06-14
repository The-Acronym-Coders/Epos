package com.teamacronymcoders.epos.character;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterStats;
import com.teamacronymcoders.epos.api.character.info.CharacterInfo;
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
            Feats.CODEC.fieldOf("feats").forGetter(ICharacterStats::getFeats),
            Codec.INT.fieldOf("maxLevel").forGetter(CharacterStats::getMaxLevel),
            CharacterInfo.CODEC.optionalFieldOf("characterInfo", new CharacterInfo()).forGetter(CharacterStats::getCharacterInfo)
        ).apply(instance, CharacterStats::new)
    );

    private final Paths paths;
    private final Skills skills;
    private final Feats feats;

    private final int maxLevel;

    private final CharacterInfo characterInfo;

    /**
     * Default Constructor
     */
    public CharacterStats() {
        this.paths = new Paths();
        this.skills = new Skills();
        this.feats = new Feats();
        this.maxLevel = 20;
        this.characterInfo = new CharacterInfo();
    }

    /**
     * Codec Constructor
     * @param paths The Character's Paths
     * @param skills The Character's Skills
     * @param feats The Character's Feats
     */
    public CharacterStats(Paths paths, Skills skills, Feats feats, int maxLevel, CharacterInfo characterInfo) {
        this.paths = paths;
        this.skills = skills;
        this.feats = feats;
        this.maxLevel = maxLevel;
        this.characterInfo = characterInfo;
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

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getCurrentLevel() {
        return this.getCharacterInfo().getCurrentLevel();
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void levelUp(int levels) {
        this.getCharacterInfo().currentLevel = Math.max(this.maxLevel, this.getCharacterInfo().getCurrentLevel() + levels);
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void levelDown(int levels) {
        this.getCharacterInfo().currentLevel = Math.min(1, this.getCharacterInfo().currentLevel = levels);
    }

    @Override
    public int getExperience() {
        return this.getCharacterInfo().getExperience();
    }

    @Override
    public void addExperience(int experience) {
        this.getCharacterInfo().experience = Math.max(Integer.MAX_VALUE, this.getCharacterInfo().experience + experience);
    }

    @Override
    public void removeExperience(int experience) {
        this.getCharacterInfo().experience = Math.max(0, this.getCharacterInfo().experience - experience);
    }

    @Override
    public CharacterInfo getCharacterInfo() {
        return this.characterInfo;
    }
}

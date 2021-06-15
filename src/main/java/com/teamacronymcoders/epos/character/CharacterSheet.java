package com.teamacronymcoders.epos.character;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.character.info.CharacterInfo;
import com.teamacronymcoders.epos.api.feat.Feats;
import com.teamacronymcoders.epos.api.path.Paths;
import com.teamacronymcoders.epos.api.skill.Skills;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraftforge.common.util.Constants.NBT;

/**
 *
 */
public class CharacterSheet implements ICharacterSheet {

    public static final Codec<CharacterSheet> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Paths.CODEC.fieldOf("paths").forGetter(ICharacterSheet::getPaths),
            Skills.CODEC.fieldOf("skills").forGetter(ICharacterSheet::getSkills),
            Feats.CODEC.fieldOf("feats").forGetter(ICharacterSheet::getFeats),
            Codec.INT.fieldOf("maxLevel").forGetter(CharacterSheet::getMaxLevel),
            CharacterInfo.CODEC.optionalFieldOf("characterInfo", new CharacterInfo()).forGetter(CharacterSheet::getCharacterInfo)
        ).apply(instance, CharacterSheet::new)
    );

    private Paths paths;
    private Skills skills;
    private Feats feats;

    private final int maxLevel;

    private CharacterInfo characterInfo;

    /**
     * Default Constructor
     */
    public CharacterSheet() {
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
    public CharacterSheet(Paths paths, Skills skills, Feats feats, int maxLevel, CharacterInfo characterInfo) {
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
        this.getCharacterInfo().currentLevel = Math.min(this.maxLevel, this.getCharacterInfo().getCurrentLevel() + levels);
    }

    // TODO: Basic implementation, Look over this later
    @Override
    public void levelDown(int levels) {
        this.getCharacterInfo().currentLevel = Math.max(1, this.getCharacterInfo().currentLevel = levels);
    }

    @Override
    public int getExperience() {
        return this.getCharacterInfo().getExperience();
    }

    @Override
    public void addExperience(int experience) {
        this.getCharacterInfo().experience = Math.min(Integer.MAX_VALUE, this.getCharacterInfo().experience + experience);
    }

    @Override
    public void removeExperience(int experience) {
        this.getCharacterInfo().experience = Math.max(0, this.getCharacterInfo().experience - experience);
    }

    @Override
    public CharacterInfo getCharacterInfo() {
        return this.characterInfo;
    }

    @Override
    public CompoundNBT serializeNBT() {
        DataResult<INBT> result = CODEC.encodeStart(NBTDynamicOps.INSTANCE, this);
        return result.result().isPresent() ? (CompoundNBT) result.result().get() : null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        DataResult<CharacterSheet> result = CODEC.parse(NBTDynamicOps.INSTANCE, nbt);
        if (result.result().isPresent()) {
            CharacterSheet sheet = result.result().get();
            this.paths = sheet.paths;
            this.skills = sheet.skills;
            this.feats = sheet.feats;
            this.characterInfo = sheet.getCharacterInfo();
        }
    }
}

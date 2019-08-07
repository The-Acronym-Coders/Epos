package com.teamacronymcoders.eposmajorum.json.deserializer;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.eposmajorum.api.skill.ISkill;
import com.teamacronymcoders.eposmajorum.skill.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SkillBuilder implements IRegistryEntryBuilder<ISkill> {
    @JsonAdapter(ITextComponent.Serializer.class)
    private ITextComponent name;
    @JsonAdapter(ITextComponent.Serializer.class)
    private ITextComponent description;
    private int maxLevel;
    private boolean hidden;

    @Override
    public ISkill build(ResourceLocation registryName) throws JsonParseException {
        return new Skill(registryName, name, description, hidden, maxLevel);
    }

    public ITextComponent getName() {
        return name;
    }

    public void setName(ITextComponent name) {
        this.name = name;
    }

    public ITextComponent getDescription() {
        return description;
    }

    public void setDescription(ITextComponent description) {
        this.description = description;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}

package com.teamacronymcoders.epos.json.deserializer;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.epos.api.path.IClass;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatures;
import com.teamacronymcoders.epos.classes.CharacterClass;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public class CharacterClassBuilder implements IRegistryEntryBuilder<IClass> {
    @JsonAdapter(value = ITextComponent.Serializer.class)
    private ITextComponent name;
    @JsonAdapter(value = ITextComponent.Serializer.class)
    private ITextComponent description;
    @JsonAdapter(value = ClassFeaturesDeserializer.class)
    private CharacterClassFeatures features;

    @Override
    public IClass build(ResourceLocation registryName) throws JsonParseException {
        try {
            return new CharacterClass(registryName, Objects.requireNonNull(this.getName(), "name was null"),
                Objects.requireNonNull(this.getDescription(), "description was null"),
                Objects.requireNonNull(this.getFeatures(), "features was null"));
        } catch (NullPointerException npe) {
            throw new JsonParseException(npe.getMessage());
        }
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

    public CharacterClassFeatures getFeatures() {
        return features;
    }

    public void setFeatures(CharacterClassFeatures features) {
        this.features = features;
    }
}

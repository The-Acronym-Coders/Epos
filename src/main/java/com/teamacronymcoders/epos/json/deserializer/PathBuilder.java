package com.teamacronymcoders.epos.json.deserializer;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
<<<<<<< cb35bacfea2792459db9e29d3a9eaac40d952883:src/main/java/com/teamacronymcoders/epos/json/deserializer/PathBuilder.java
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.path.Path;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatures;
=======
import com.teamacronymcoders.eposmajorum.api.path.IPath;
import com.teamacronymcoders.eposmajorum.api.pathfeature.PathFeatures;
import com.teamacronymcoders.eposmajorum.path.Path;
>>>>>>> Reformat Code and Optimize Imports:src/main/java/com/teamacronymcoders/eposmajorum/json/deserializer/PathBuilder.java
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

public class PathBuilder implements IRegistryEntryBuilder<IPath> {
    @JsonAdapter(value = ITextComponent.Serializer.class)
    private ITextComponent name;
    @JsonAdapter(value = ITextComponent.Serializer.class)
    private ITextComponent description;
    @JsonAdapter(value = PathFeaturesDeserializer.class)
    private PathFeatures features;

    @Override
    public IPath build(ResourceLocation registryName) throws JsonParseException {
        try {
            return new Path(registryName, Objects.requireNonNull(this.getName(), "name was null"),
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

    public PathFeatures getFeatures() {
        return features;
    }

    public void setFeatures(PathFeatures features) {
        this.features = features;
    }
}
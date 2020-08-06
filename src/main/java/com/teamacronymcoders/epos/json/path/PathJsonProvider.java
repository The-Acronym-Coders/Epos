package com.teamacronymcoders.epos.json.path;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hrznstudio.titanium.json.IJsonProvider;
import com.teamacronymcoders.epos.path.Path;
import net.minecraft.util.ResourceLocation;

public class PathJsonProvider implements IJsonProvider<Path> {

    @Override
    public Path provide(ResourceLocation resourceLocation, JsonObject jsonObject) throws JsonParseException {

        return null;
    }

}

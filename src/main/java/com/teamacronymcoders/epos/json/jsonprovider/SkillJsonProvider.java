package com.teamacronymcoders.epos.json.jsonprovider;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.skill.ISkill;
import com.teamacronymcoders.epos.skill.Skill;
import net.minecraft.util.ResourceLocation;

public class SkillJsonProvider implements IJsonProvider<ISkill> {
    @Override
    public ISkill provide(ResourceLocation key, JsonObject jsonObject) throws JsonParseException {
        return new Skill(key, JsonUtils.getTranslation(jsonObject, "name", "skill", key),
                JsonUtils.getTranslation(jsonObject, "description", "skill", key),
                JsonUtils.getBool(jsonObject, "hidden", false),
                JsonUtils.getInt(jsonObject, "maxLevel", 50));
    }
}

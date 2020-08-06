package com.teamacronymcoders.epos.path.feature.levelup.skill;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.path.feature.IPathFeature;
import com.teamacronymcoders.epos.api.path.feature.PathFeatureProvider;
import com.teamacronymcoders.epos.api.skill.ISkill;

public class LevelUpSkillFeatureProvider extends PathFeatureProvider {
    @Override
    public IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String skillName = JsonUtils.getString(jsonObject, "skill");
        int levels = JsonUtils.getInt(jsonObject, "level");
        ISkill skill = EposAPI.SKILL_REGISTRY.getEntry(skillName);
        if (skill == null) {
            throw new JsonParseException("No Skill found for name: " + skillName);
        }
        return new LevelUpSkillFeature(skill, levels);
    }
}

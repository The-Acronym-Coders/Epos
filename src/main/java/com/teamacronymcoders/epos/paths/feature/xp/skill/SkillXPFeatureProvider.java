package com.teamacronymcoders.epos.classes.feature.xp.skill;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.path.feature.IPathFeature;
import com.teamacronymcoders.epos.api.path.feature.PathFeatureProvider;
import com.teamacronymcoders.epos.api.skill.ISkill;

public class SkillXPFeatureProvider extends PathFeatureProvider {
    @Override
    public IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String skillName = JsonUtils.getString(jsonObject, "skill");
        int xpAmount = JsonUtils.getInt(jsonObject, "amount");
        ISkill skill = EposAPI.SKILL_REGISTRY.getEntry(skillName);
        if (skill != null) {
            return new SkillXPFeature(skill, xpAmount);
        } else {
            throw new JsonParseException("No Skill found for name: " + skillName);
        }
    }
}

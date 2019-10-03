package com.teamacronymcoders.epos.pathfeature.skillxp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeature;
import com.teamacronymcoders.epos.api.pathfeature.PathFeatureProvider;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class SkillXPFeatureProvider extends PathFeatureProvider {
    @Override
    public IPathFeature provide(JsonObject jsonObject) throws JsonParseException {
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

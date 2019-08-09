package com.teamacronymcoders.epos.pathfeature.levelupskill;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeature;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LevelUpSkillFeatureProvider implements IPathFeatureProvider {
    private final ResourceLocation registryName = new ResourceLocation(EposAPI.ID, "level_skill");

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

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}

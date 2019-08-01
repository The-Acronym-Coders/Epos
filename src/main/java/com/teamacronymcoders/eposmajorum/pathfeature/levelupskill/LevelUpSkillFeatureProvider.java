package com.teamacronymcoders.eposmajorum.pathfeature.levelupskill;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.json.JsonUtils;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeature;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeatureProvider;
import com.teamacronymcoders.eposmajorum.api.skill.ISkill;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LevelUpSkillFeatureProvider implements IPathFeatureProvider {
    private final ResourceLocation registryName = new ResourceLocation(EposAPI.ID, "level_skill");

    @Override
    public IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String skillName = JsonUtils.getString(jsonObject, "skill");
        int levels = JsonUtils.getInt(jsonObject, "level");
        ISkill skill = EposAPI.SKILL_REGISTRY.getEntry(skillName);
        if (skill != null) {
            return new LevelUpSkillFeature(skill, levels);
        } else {
            throw new JsonParseException("No Skill found for name: " + skillName);
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}

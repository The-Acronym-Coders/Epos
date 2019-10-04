package com.teamacronymcoders.epos.requirements;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public class SkillRequirement implements IRequirement {

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        return null;
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return false;
    }
}
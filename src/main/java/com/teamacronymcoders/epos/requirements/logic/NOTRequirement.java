package com.teamacronymcoders.epos.requirements.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;

public class NOTRequirement implements IRequirement {

    @Nonnull
    private final IRequirement requirement;

    public NOTRequirement(@Nonnull IRequirement requirement) {
        this.requirement = requirement;
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        return requirement.getToolTip(!matches);
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return !requirement.isMet(entity, stats);
    }
}
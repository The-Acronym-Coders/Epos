package com.teamacronymcoders.epos.requirement.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FalseRequirement implements IRequirement {

    public static final FalseRequirement INSTANCE = new FalseRequirement();

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file
        return new TranslationTextComponent("requirement.epos.tooltip.false");
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return false;
    }

    @Nonnull
    @Override
    public RequirementComparison compare(IRequirement other) {
        return other == this ? RequirementComparison.IDENTICAL : RequirementComparison.INCOMPATIBLE;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return other instanceof FalseRequirement;
    }
}
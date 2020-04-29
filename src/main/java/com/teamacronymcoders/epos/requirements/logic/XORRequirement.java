package com.teamacronymcoders.epos.requirements.logic;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import com.teamacronymcoders.epos.api.requirements.RequirementComparision;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class XORRequirement extends DoubleRequirement {

    public XORRequirement(@Nonnull IRequirement leftRequirement, @Nonnull IRequirement rightRequirement) {
        super(leftRequirement, rightRequirement);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        //TODO: Add the lang key to the lang file with the proper format, and matching values, as one may match and the other may not
        return new TranslationTextComponent("requirement.epos.tooltip.xor", leftRequirement.getToolTip(matches), rightRequirement.getToolTip(matches));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return leftRequirement.isMet(entity, stats) != rightRequirement.isMet(entity, stats);
    }

    @Override
    protected boolean isInstance(DoubleRequirement requirement) {
        return requirement instanceof XORRequirement;
    }

    @Nullable
    @Override
    protected RequirementComparision getComparision(RequirementComparision leftComparision, RequirementComparision rightComparision) {
        if (leftComparision == RequirementComparision.IDENTICAL && rightComparision == RequirementComparision.IDENTICAL) {
            return RequirementComparision.IDENTICAL;
        }
        //TODO: Implement
        return null;
    }

    @Nullable
    @Override
    protected RequirementComparision getSingleComparision(RequirementComparision leftComparision, RequirementComparision rightComparision) {
        //TODO: Implement
        return null;
    }
}
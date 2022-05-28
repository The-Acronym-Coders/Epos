package com.teamacronymcoders.epos.requirement;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class IntegerRequirement implements IRequirement {

    private final int value;

    public IntegerRequirement(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof IntegerRequirement && value == ((IntegerRequirement) obj).value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        return new StringTextComponent(Integer.toString(value));
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return false;
    }

    @Nonnull
    @Override
    public RequirementComparison compare(IRequirement other) {
        if (other == this) {
            return RequirementComparison.IDENTICAL;
        } else if (other instanceof IntegerRequirement) {
            int otherValue = ((IntegerRequirement) other).value;
            if (value == otherValue) {
                return RequirementComparison.IDENTICAL;
            } else if (value < otherValue) {
                //Other one is a larger number which means it is less restrictive than us
                return RequirementComparison.LESS_RESTRICTIVE_THAN;
            }//else value > otherValue
            //Other one is a smaller number which means it is more restrictive than us
            return RequirementComparison.MORE_RESTRICTIVE_THAN;
        }
        return RequirementComparison.INCOMPATIBLE;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return other instanceof IntegerRequirement;
    }
}
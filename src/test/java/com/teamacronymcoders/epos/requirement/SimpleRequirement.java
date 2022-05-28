package com.teamacronymcoders.epos.requirement;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
import com.teamacronymcoders.epos.api.requirement.IRequirement;
import com.teamacronymcoders.epos.api.requirement.RequirementComparison;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/**
 * Simple implementation of IRequirement that can be compared by the name it got passed.
 *
 * Used for testing locks
 */
public class SimpleRequirement implements IRequirement {

    @Nonnull
    private final String name;

    public SimpleRequirement(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof SimpleRequirement && name.equals(((SimpleRequirement) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Nonnull
    @Override
    public ITextComponent getToolTip(boolean matches) {
        return new StringTextComponent(name);
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
        } else if (other instanceof SimpleRequirement) {
            if (name.equals(((SimpleRequirement) other).name)) {
                return RequirementComparison.IDENTICAL;
            }
            return RequirementComparison.TYPE_MATCHES;
        }
        return RequirementComparison.INCOMPATIBLE;
    }

    @Override
    public boolean canCompareWith(IRequirement other) {
        return other instanceof SimpleRequirement;
    }
}
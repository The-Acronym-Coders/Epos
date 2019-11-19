package com.teamacronymcoders.epos.api.requirements;

import com.teamacronymcoders.epos.api.characterstats.ICharacterStats;
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
    private final ITextComponent tooltip;

    public SimpleRequirement(@Nonnull String name) {
        this.name = name;
        this.tooltip = new StringTextComponent(name);
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
        return tooltip;
    }

    @Override
    public boolean isMet(@Nonnull LivingEntity entity, @Nonnull ICharacterStats stats) {
        return false;
    }

    @Nonnull
    @Override
    public RequirementComparision compare(IRequirement other) {
        if (other == this) {
            return RequirementComparision.IDENTICAL;
        } else if (other instanceof SimpleRequirement) {
            if (name.equals(((SimpleRequirement) other).name)) {
                return RequirementComparision.IDENTICAL;
            }
            return RequirementComparision.TYPE_MATCHES;
        }
        return RequirementComparision.INCOMPATIBLE;
    }
}
package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

public class DimensionTypeLockKey implements ILockKey {

    @Nonnull
    private final DimensionType dimension;

    public DimensionTypeLockKey(@Nonnull DimensionType dimension) {
        this.dimension = dimension;
    }

    @Nullable
    public static DimensionTypeLockKey fromObject(@Nonnull Object object) {
        if (object instanceof DimensionType) {
            return new DimensionTypeLockKey((DimensionType) object);
        } else if (object instanceof Dimension) {
            return new DimensionTypeLockKey(((Dimension) object).getType());
        } else if (object instanceof IWorld) {
            return new DimensionTypeLockKey(((IWorld) object).getDimension().getType());
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof DimensionTypeLockKey && dimension.equals(((DimensionTypeLockKey) obj).dimension);
    }

    @Override
    public int hashCode() {
        return dimension.hashCode();
    }
}
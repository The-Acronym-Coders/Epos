package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class DimensionTypeLockKey implements ILockKey {

    @Nonnull
    private final RegistryKey<World> dimension;

    public DimensionTypeLockKey(@Nonnull RegistryKey<World> dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof DimensionTypeLockKey && dimension == ((DimensionTypeLockKey) obj).dimension;
    }

    @Override
    public int hashCode() {
        return dimension.hashCode();
    }

    @Nullable
    public static DimensionTypeLockKey fromObject(@Nonnull Object object) {
        if (object instanceof World) {
            return new DimensionTypeLockKey(((World) object).getDimensionKey());
        }
        return null;
    }
}
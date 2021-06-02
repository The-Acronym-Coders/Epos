package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

/**
 * Used for locking worlds.
 */
public class WorldLockKey implements ILockKey {

    @Nonnull
    private final RegistryKey<World> dimension;

    public WorldLockKey(@Nonnull RegistryKey<World> dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof WorldLockKey && dimension == ((WorldLockKey) obj).dimension;
    }

    @Override
    public int hashCode() {
        return dimension.hashCode();
    }

    @Nullable
    public static WorldLockKey fromObject(@Nonnull Object object) {
        if (object instanceof World) {
            return new WorldLockKey(((World) object).getDimensionKey());
        }
        return null;
    }
}
package com.teamacronymcoders.epos.locks.key.entity;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public abstract class EntityLockKey<TYPE extends Entity> implements ILockKey {

    @Nonnull
    private final EntityType<TYPE> entityType;

    protected EntityLockKey(@Nonnull EntityType<TYPE> entityType) {
        this.entityType = entityType;
    }

    @Nullable
    protected static EntityType<? extends Entity> getEntityType(@Nonnull Object object) {
        if (object instanceof Entity) {
            return ((Entity) object).getType();
        } else if (object instanceof EntityType) {
            return (EntityType<? extends Entity>) object;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof EntityLockKey && entityType.equals(((EntityLockKey<?>) obj).entityType);
    }

    @Override
    public int hashCode() {
        return entityType.hashCode();
    }
}
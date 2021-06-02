package com.teamacronymcoders.epos.locks.key.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class EntityTameKey<TYPE extends Entity> extends EntityLockKey<TYPE> {

    public EntityTameKey(EntityType<TYPE> entityType) {
        super(entityType);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityTameKey && super.equals(obj);
    }

    @Nullable
    public static EntityTameKey<?> fromObject(@Nonnull Object object) {
        EntityType<?> type = getEntityType(object);
        return type == null ? null : new EntityTameKey<>(type);
    }
}
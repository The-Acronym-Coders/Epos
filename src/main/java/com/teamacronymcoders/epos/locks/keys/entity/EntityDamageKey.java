package com.teamacronymcoders.epos.locks.keys.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class EntityDamageKey<TYPE extends Entity> extends EntityLockKey<TYPE> {

    public EntityDamageKey(EntityType<TYPE> entityType) {
        super(entityType);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntityDamageKey && super.equals(obj);
    }

    @Nullable
    public static EntityDamageKey fromObject(@Nonnull Object object) {
        EntityType<? extends Entity> type = getEntityType(object);
        return type == null ? null : new EntityDamageKey<>(type);
    }
}
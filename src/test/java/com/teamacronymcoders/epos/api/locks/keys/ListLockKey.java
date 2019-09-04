package com.teamacronymcoders.epos.api.locks.keys;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ListLockKey<TYPE> implements IParentLockKey {

    private final List<TYPE> values;

    private ListLockKey(List<TYPE> values) {
        this.values = values;
    }

    @Nullable
    public static ListLockKey<?> fromObject(@Nonnull Object object) {
        if (object instanceof List<?>) {
            return new ListLockKey<>((List<?>) object);
        } else if (object instanceof String) {
            String string = (String) object;
            return string.contains(",") ? new ListLockKey<>(Arrays.asList(string.split("\\s*,\\s*"))) : null;
        }
        return null;
    }

    @Nonnull
    @Override
    public List<IRequirement> getSubRequirements() {
        List<IRequirement> requirements = new ArrayList<>();
        for (TYPE value : values) {
            requirements.addAll(EposAPI.LOCK_REGISTRY.getLocks(value));
        }
        return requirements;
    }
}
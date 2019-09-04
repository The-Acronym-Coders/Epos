package com.teamacronymcoders.epos.api.locks.keys;

import com.teamacronymcoders.epos.api.locks.LockRegistry;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import java.util.List;
import javax.annotation.Nonnull;

public interface IParentLockKey extends ILockKey {

    /**
     * Retrieves any sub requirements this key may have. This usually can be implemented by calling {@link LockRegistry#getLocks(Class, Object[])}
     *
     * @return A RequirementHolder of the sub requirements.
     */
    @Nonnull
    List<IRequirement> getSubRequirements();
    //TODO: Update javadocs reason this is a list is so that the higher tier logic handlers can decide how to merge ALL the different requirements together
    // This is not a set in case something wants to know if there are duplicates and merge them differently
}
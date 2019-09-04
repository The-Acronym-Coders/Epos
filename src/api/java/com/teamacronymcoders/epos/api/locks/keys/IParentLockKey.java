package com.teamacronymcoders.epos.api.locks.keys;

import com.teamacronymcoders.epos.api.locks.LockRegistry;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;

public interface IParentLockKey extends ILockKey {

    /**
     * Retrieves any sub requirements this key may have. This usually can be implemented by calling {@link LockRegistry#getLocks(Collection)}}.
     *
     * @return A List of all the sub requirements.
     */
    @Nonnull
    List<IRequirement> getSubRequirements();
}
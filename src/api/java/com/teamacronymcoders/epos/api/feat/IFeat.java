package com.teamacronymcoders.epos.api.feat;

import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.registry.IDynamic;

// TODO: Document Main Interface Object
public interface IFeat extends IDynamic<IFeat, IFeat>, IDescribable {

    /**
     * Returns a boolean value as to whether the {@link IFeat} is unlocked by the Character or not.
     * @return Returns whether the feat is unlocked or not.
     */
    boolean isUnlocked();

    /**
     * Returns a boolean value as to wheter the {@link IFeat} is an Tickable Ability or not!
     * @return Returns whether the feat is an active ability or not.
     */
    boolean isAbility();

}

package com.teamacronymcoders.epos.api.feat;

import com.teamacronymcoders.epos.api.IDescribable;
import net.ashwork.dynamicregistries.entry.IDynamicEntry;

// TODO: Document Main Interface Object
public interface IFeat extends IDynamicEntry<IFeat>, IDescribable {

    /**
     * Returns a boolean value as to wheter the {@link IFeat} is an Tickable Ability or not!
     *
     * @return Returns whether the feat is an active ability or not.
     */
    boolean isAbility();

    FeatInfo createFeatInfo();

}

package com.teamacronymcoders.epos.feat;

import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.registry.FeatRegistrar;
import net.ashwork.dynamicregistries.entry.DynamicEntry;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraft.network.chat.MutableComponent;

public class Feat extends DynamicEntry<IFeat> implements IFeat {

    private final MutableComponent name;
    private final MutableComponent description;
    private final boolean isAbility;

    /**
     * Constructor for Feat(s).
     *
     * @param name        The Name of the Feat.
     * @param description The Description of the Feat.
     * @param isAbility   If the Feat is an Active 'Ability'.
     */
    public Feat(MutableComponent name, MutableComponent description, boolean isAbility) {
        this.name = name;
        this.description = description;
        this.isAbility = isAbility;
    }

    @Override
    public MutableComponent getName() {
        return this.name;
    }

    @Override
    public MutableComponent getDescription() {
        return this.description;
    }

    @Override
    public boolean isAbility() {
        return this.isAbility;
    }

    @Override
    public ICodecEntry<? extends IFeat, ?> codec() {
        return FeatRegistrar.FEAT_SERIALIZER.get();
    }
}

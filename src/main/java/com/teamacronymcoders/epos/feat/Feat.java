package com.teamacronymcoders.epos.feat;

import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.registry.ISerializer;
import com.teamacronymcoders.epos.registry.FeatRegistrar;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Feat extends ForgeRegistryEntry<IFeat> implements IFeat {

    private final IFormattableTextComponent name;
    private final IFormattableTextComponent description;
    private final boolean isAbility;

    /**
     * Constructor for Feat(s).
     * @param name The Name of the Feat.
     * @param description The Description of the Feat.
     * @param isAbility If the Feat is an Active 'Ability'.
     */
    public Feat(IFormattableTextComponent name, IFormattableTextComponent description, boolean isAbility) {
        this.name = name;
        this.description = description;
        this.isAbility = isAbility;
    }

    @Override
    public IFormattableTextComponent getName() {
        return this.name;
    }

    @Override
    public IFormattableTextComponent getDescription() {
        return this.description;
    }

    @Override
    public boolean isAbility() {
        return this.isAbility;
    }

    @Override
    public ISerializer<? extends IFeat, ?> serializer() {
        return FeatRegistrar.FEAT_SERIALIZER.get();
    }
}

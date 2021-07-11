package com.teamacronymcoders.epos.api.builder;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.mojang.datafixers.util.Function3;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.feat.Feat;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatBuilder {

    public static final List<IFeat> BUILT_FEATS = new ArrayList<>();

    public static FeatBuilder create(ResourceLocation registryName) {
        return new FeatBuilder(registryName, Feat::new);
    }

    private final ResourceLocation registryName;
    private final Function3<IFormattableTextComponent, IFormattableTextComponent, Boolean, IFeat> factory;
    private NonNullSupplier<IFormattableTextComponent> name;
    private NonNullSupplier<IFormattableTextComponent> description;
    private NonNullSupplier<Boolean> isAbility = () -> false;
    @Nonnull
    private final List<EventManager.ISubscribe> eventManagers;

    private FeatBuilder(ResourceLocation registryName, Function3<IFormattableTextComponent, IFormattableTextComponent, Boolean, IFeat> factory) {
        this.registryName = registryName;
        this.factory = factory;
        this.eventManagers = new ArrayList<>();
    }

    public FeatBuilder name(NonNullSupplier<IFormattableTextComponent> name) {
        this.name = name;
        return this;
    }

    public FeatBuilder description(NonNullSupplier<IFormattableTextComponent> description) {
        this.description = description;
        return this;
    }

    public FeatBuilder isAbility(NonNullSupplier<Boolean> isAbility) {
        this.isAbility = isAbility;
        return this;
    }

    public FeatBuilder withEventManager(EventManager.ISubscribe eventManager) {
        this.eventManagers.add(eventManager);
        return this;
    }

    public FeatBuilder withEventManagers(EventManager.ISubscribe... eventManagers) {
        this.eventManagers.addAll(Arrays.asList(eventManagers));
        return this;
    }

    @NonnullType
    public IFeat createEntry() {
        IFeat feat = factory.apply(this.name.get(), this.description.get(), this.isAbility.get());
        feat.setRegistryName(this.registryName);
        BUILT_FEATS.add(feat);
        eventManagers.forEach(EventManager.ISubscribe::subscribe);
        return feat;
    }

}

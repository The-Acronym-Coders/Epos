package com.teamacronymcoders.epos.api.builder;

import com.mojang.datafixers.util.Function3;
import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.feat.Feat;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nonnull;
import java.util.*;

public class FeatBuilder {

    public static final Map<ResourceLocation, IFeat> BUILT_FEATS = new HashMap<>();

    public static FeatBuilder create(ResourceLocation registryName) {
        return new FeatBuilder(registryName, Feat::new);
    }

    private final ResourceLocation registryName;
    private final Function3<MutableComponent, MutableComponent, Boolean, IFeat> factory;
    private NonNullSupplier<MutableComponent> name;
    private NonNullSupplier<MutableComponent> description;
    private NonNullSupplier<Boolean> isAbility = () -> false;
    @Nonnull
    private final List<EventManager.ISubscribe> eventManagers;

    private FeatBuilder(ResourceLocation registryName, Function3<MutableComponent, MutableComponent, Boolean, IFeat> factory) {
        this.registryName = registryName;
        this.factory = factory;
        this.eventManagers = new ArrayList<>();
    }

    public FeatBuilder name(NonNullSupplier<MutableComponent> name) {
        this.name = name;
        return this;
    }

    public FeatBuilder description(NonNullSupplier<MutableComponent> description) {
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
        BUILT_FEATS.put(this.registryName, feat);
        eventManagers.forEach(EventManager.ISubscribe::subscribe);
        return feat;
    }

}

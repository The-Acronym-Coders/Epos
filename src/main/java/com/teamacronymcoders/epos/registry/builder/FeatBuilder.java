package com.teamacronymcoders.epos.registry.builder;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.mojang.datafixers.util.Function3;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.feat.Feat;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.util.text.IFormattableTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatBuilder<T extends IFeat, P> extends AbstractBuilder<IFeat, T, P, FeatBuilder<T, P>> {

    public static <P> FeatBuilder<Feat, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
        return new FeatBuilder<>(owner, parent, name, callback, Feat::new);
    }

    private final Function3<IFormattableTextComponent, IFormattableTextComponent, Boolean, T> factory;
    private NonNullSupplier<IFormattableTextComponent> name;
    private NonNullSupplier<IFormattableTextComponent> description;
    private NonNullSupplier<Boolean> isAbility;
    @Nonnull
    private final List<EventManager.ISubscribe> eventManagers;


    public FeatBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, Function3<IFormattableTextComponent, IFormattableTextComponent, Boolean, T> factory) {
        super(owner, parent, name, callback, IFeat.class);
        this.factory = factory;
        this.eventManagers = new ArrayList<>();
    }

    public FeatBuilder<T, P> name(NonNullSupplier<IFormattableTextComponent> name) {
        this.name = name;
        return this;
    }

    public FeatBuilder<T, P> description(NonNullSupplier<IFormattableTextComponent> description) {
        this.description = description;
        return this;
    }

    public FeatBuilder<T, P> isAbility(NonNullSupplier<Boolean> isAbility) {
        this.isAbility = isAbility;
        return this;
    }

    public FeatBuilder<T, P> withEventManager(EventManager.ISubscribe eventManager) {
        this.eventManagers.add(eventManager);
        return this;
    }

    public FeatBuilder<T, P> withEventManagers(EventManager.ISubscribe... eventManagers) {
        this.eventManagers.addAll(Arrays.asList(eventManagers));
        return this;
    }

    @Override
    protected @NonnullType T createEntry() {
        eventManagers.forEach(EventManager.ISubscribe::subscribe);
        return factory.apply(this.name.get(), this.description.get(), this.isAbility.get());
    }


}

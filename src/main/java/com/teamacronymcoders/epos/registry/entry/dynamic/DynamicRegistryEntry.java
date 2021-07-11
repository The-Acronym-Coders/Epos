package com.teamacronymcoders.epos.registry.entry.dynamic;

import com.teamacronymcoders.epos.registry.EposRegistrate;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.ashwork.dynamicregistries.entry.IDynamicEntry;
import net.ashwork.dynamicregistries.registry.DynamicRegistry;
import net.ashwork.dynamicregistries.registry.IDynamicRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;

public class DynamicRegistryEntry<T extends IDynamicEntry<? super T>, U extends ICodecEntry<T, U>> implements NonNullSupplier<T> {
    private static DynamicRegistryEntry<?, ?> EMPTY;

    static {
        try {
            IDynamicEntry<?> entry;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends IDynamicEntry<? super T>, U extends ICodecEntry<T, U>> DynamicRegistryEntry<T, U> empty() {
        @SuppressWarnings("unchecked")
        DynamicRegistryEntry<T, U> t = (DynamicRegistryEntry<T, U>) EMPTY;
        return t;
    }

    private interface Exclusions<T extends IDynamicEntry<? super T>, U extends ICodecEntry<T, U>> {
        T get();

        RegistryObject<T> filter(BiPredicate<? super T, U> predicate);

        void updateReference(IDynamicRegistry<? extends T, U> registry);
    }

    private final EposRegistrate owner;
    @Nullable
    private final DynamicRegistryEntry<T, U> delegate;

    public DynamicRegistryEntry(EposRegistrate owner, @Nullable DynamicRegistryEntry<T, U> delegate) {
        if (EMPTY != null && owner == null) throw new NullPointerException("Owner must not be null");
        if (EMPTY != null && delegate == null) throw new NullPointerException("Delegate must not be null");
        this.owner = owner;
        this.delegate = delegate;
    }

    public void updateReference(DynamicRegistry<? super T, U> registry) {
        //RegistryObject<T> delegate = this.delegate;
        Objects.requireNonNull(delegate, "Registry entry is empty").updateReference(registry);
    }

    @Override
    @NonnullType
    public T get() {
        return Objects.requireNonNull(getUnchecked(), () -> delegate == null ? "Registry entry is empty" : "Registry entry not present: " + delegate.get().getRegistryName());
    }

    @Nullable
    public T getUnchecked() {
        //RegistryObject<T> delegate = this.delegate;
        return delegate == null ? null : delegate.orElse(null);
    }

//    public <R extends IDynamicEntry<R>, E extends ICodecEntry<R, E>> DynamicRegistryEntry<R, E> getSibling(Class<? super R> registryType) {
//        return this == EMPTY ? empty() : owner.get(getId().getPath(), (Class<R>) registryType);
//    }
//
//    public <R extends IDynamicEntry<R>, E extends ICodecEntry<R, E>> DynamicRegistryEntry<R, E> getSibling(IForgeRegistry<R> registry) {
//        return getSibling(registry.getRegistrySuperType());
//    }

    public DynamicRegistryEntry<T, U> filter(BiPredicate<? super T, U> predicate) {
        Objects.requireNonNull(predicate);
//        if (!isPresent() || predicate.test(get())) {
//            return this;
//        }
        return empty();
    }

    public <R extends IDynamicEntry<? super T>> boolean is(R entry) {
        return get() == entry;
    }

    @SuppressWarnings("unchecked")
    protected static <E extends DynamicRegistryEntry<?, ? extends ICodecEntry<?, ?>>> E cast(Class<? super E> clazz, DynamicRegistryEntry<?, ?> entry) {
        if (clazz.isInstance(entry)) {
            return (E) entry;
        }
        throw new IllegalArgumentException("Could not convert DynamicRegistryEntry: expecting " + clazz + ", found " + entry.getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DynamicRegistryEntry)) return false;
        final DynamicRegistryEntry<?, ?> other = (DynamicRegistryEntry<?, ?>) o;
        if (!other.canEqual((Object) this)) return false;
        final Object thisDelegate = this.delegate;
        final Object otherDelegate = other.delegate;
        return Objects.equals(thisDelegate, otherDelegate);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DynamicRegistryEntry;
    }

    @Override
    public int hashCode() {
        final int prime = 59;
        int result = 1;
        final Object delegate = this.delegate;
        result = result * prime + (delegate == null ? 43 : delegate.hashCode());
        return result;
    }

    public ResourceLocation getId() {
        return this.delegate.getId();
    }

    public Stream<T> stream() {
        return this.delegate.stream();
    }

    public boolean isPresent() {
        return this.delegate.isPresent();
    }

    public void ifPresent(final Consumer<? super T> consumer) {
        this.delegate.ifPresent(consumer);
    }

    public <U extends Object> Optional<U> map(final Function<? super T, ? extends U> mapper) {
        return this.delegate.map(mapper);
    }

    public <U extends Object> Optional<U> flatMap(final Function<? super T, java.util.Optional<U>> mapper) {
        return this.delegate.flatMap(mapper);
    }

    public <U extends Object> Supplier<U> lazyMap(final Function<? super T, ? extends U> mapper) {
        return this.delegate.lazyMap(mapper);
    }

    public T orElse(final T other) {
        return this.delegate.orElse(other);
    }

    public T orElseGet(final Supplier<? extends T> other) {
        return this.delegate.orElseGet(other);
    }

    public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
        return this.delegate.<X>orElseThrow(exceptionSupplier);
    }
}

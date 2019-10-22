package com.teamacronymcoders.epos.utils;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * A few methods to helps with LazyOptional
 * Grabbed from Mekanism
 * Code: https://github.com/mekanism/Mekanism/blob/1.14.x/src/main/java/mekanism/common/base/LazyOptionalHelper.java
 * License: https://github.com/mekanism/Mekanism/tree/1.14.x#license
 */
public class LazyOptionalHelper<T> {

    private LazyOptional<T> lazyOptional;

    public static <U> LazyOptionalHelper<U> of(@Nonnull ICapabilityProvider provider, @Nonnull Capability<U> capability) {
        return new LazyOptionalHelper<>(provider.getCapability(capability));
    }

    public static <U> LazyOptionalHelper<U> of(@Nonnull LazyOptional<U> lazyOptional) {
        return new LazyOptionalHelper<>(lazyOptional);
    }

    private LazyOptionalHelper(@Nonnull LazyOptional<T> lazyOptional) {
        this.lazyOptional = lazyOptional;
    }

    public boolean isPresent() {
        return lazyOptional.isPresent();
    }

    /**
     * @return The value of the optional or null if there is none.
     */
    @Nullable
    public T getValue() {
        if (lazyOptional.isPresent()) {
            return lazyOptional.orElseThrow(() ->
                    new RuntimeException("Failed to retrieve value of lazy optional when it said it was present"));
        }
        return null;
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(getValue());
    }

    public void ifPresent(@Nonnull NonNullConsumer<? super T> consumer) {
        lazyOptional.ifPresent(consumer);
    }

    public void ifPresentElse(NonNullConsumer<? super T> presentConsumer, Runnable elseConsumer) {
        if (isPresent()) {
            lazyOptional.ifPresent(presentConsumer);
        } else {
            elseConsumer.run();
        }
    }

    @Nullable
    public <RESULT> RESULT getIfPresent(Function<? super T, RESULT> function) {
        if (isPresent()) {
            return function.apply(getValue());
        }
        return null;
    }

    //For when the result is not a constant so we don't want to evaluate it if our LazyOptional is present
    public <RESULT> RESULT getIfPresentElseDo(Function<? super T, RESULT> presentFunction, Supplier<RESULT> elseResult) {
        if (isPresent()) {
            return presentFunction.apply(getValue());
        }
        return elseResult.get();
    }

    public <RESULT> RESULT getIfPresentElse(Function<? super T, RESULT> presentFunction, RESULT elseResult) {
        if (isPresent()) {
            return presentFunction.apply(getValue());
        }
        return elseResult;
    }

    public boolean matches(Predicate<? super T> predicate) {
        if (isPresent()) {
            return predicate.test(getValue());
        }
        return false;
    }
}

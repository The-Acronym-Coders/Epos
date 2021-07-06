/*
 * MIT License
 *
 * Copyright (c) 2019-2021 Team Acronym Coders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.teamacronymcoders.epos.api.registry;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiConsumer;

// TODO: Finish, currently doesn't exist anywhere
public interface IDynamicRegistry<V extends IDynamic<V, ?>, S extends ISerializer<V, ?>> {

    ResourceLocation getRegistryName();

    Class<V> getSuperType();

    void register(V val);

    boolean containsKey(ResourceLocation loc);

    boolean containsValue(V value);

    boolean isEmpty();

    void clear();

    @Nullable
    V get(ResourceLocation loc);

    default Optional<V> getOptional(ResourceLocation loc) {
        return Optional.ofNullable(this.get(loc));
    }

    void forEach(BiConsumer<ResourceLocation, V> consumer);
}

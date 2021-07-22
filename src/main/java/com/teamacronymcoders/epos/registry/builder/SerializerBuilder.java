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

package com.teamacronymcoders.epos.registry.builder;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.ashwork.dynamicregistries.entry.ICodecEntry;
import net.minecraftforge.common.util.NonNullSupplier;

public class SerializerBuilder<R extends ICodecEntry<?, R>, T extends R, P>
        extends AbstractBuilder<R, T, P, SerializerBuilder<R, T, P>> {

    private final NonNullSupplier<? extends T> factory;

    public SerializerBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback,
                             Class<? super R> registryType, NonNullSupplier<? extends T> factory) {
        super(owner, parent, name, callback, registryType);
        this.factory = factory;
    }

    @Override
    protected @NonnullType T createEntry() {
        return this.factory.get();
    }
}

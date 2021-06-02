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

import java.util.function.Supplier;

import com.teamacronymcoders.epos.registry.DynamicRegistryHandler;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class DynamicRegistryBuilder<V extends IDynamic<V, ?>, S extends ISerializer<V, S>> {

    private ResourceLocation registryName;
    private Class<V> registryType;
    private Supplier<IForgeRegistry<S>> serializerRegistry;
    private boolean sync = true;
    private boolean saveToDisc = true;

    public DynamicRegistryBuilder<V, S> setName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    public DynamicRegistryBuilder<V, S> setType(Class<V> type) {
        this.registryType = type;
        return this;
    }

    public DynamicRegistryBuilder<V, S> setSerializer(Supplier<IForgeRegistry<S>> serializerRegistry) {
        this.serializerRegistry = serializerRegistry;
        return this;
    }

    public DynamicRegistryBuilder<V, S> disableSync() {
        this.sync = false;
        return this;
    }

    public DynamicRegistryBuilder<V, S> disableSaving() {
        this.saveToDisc = false;
        return this;
    }

    public ResourceLocation getName() {
        return this.registryName;
    }

    public Class<V> getType() {
        return this.registryType;
    }

    public IForgeRegistry<S> getSerializer() {
        return this.serializerRegistry.get();
    }

    public boolean shouldSync() {
        return this.sync;
    }

    public boolean shouldSave() {
        return this.saveToDisc;
    }

    public IDynamicRegistry<V, S> create() {
        return DynamicRegistryHandler.INSTANCE.createRegistry(this);
    }
}

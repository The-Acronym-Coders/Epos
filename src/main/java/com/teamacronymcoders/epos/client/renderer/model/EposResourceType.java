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

package com.teamacronymcoders.epos.client.renderer.model;

import com.google.common.base.Supplier;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.util.EposRegistries;
import net.ashwork.dynamicregistries.DynamicRegistryManager;
import net.ashwork.dynamicregistries.entry.IDynamicEntry;
import net.ashwork.dynamicregistries.registry.DynamicRegistry;
import net.ashwork.dynamicregistries.registry.IDynamicRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.*;

public class EposResourceType implements ExistingFileHelper.IResourceType {

    public static final EposResourceType SKILL = constructResourceType(new ResourceLocation(Epos.ID, "skill"));

    public static final EposResourceType constructResourceType(ResourceLocation registryName) {
        return new EposResourceType(() -> DynamicRegistryManager.DYNAMIC.getRegistry(registryName),
                new ResourceLocation(registryName.getNamespace(), "general/" + registryName.getPath()));
    }

    private static final RandomSource RANDOM = RandomSource.create();
    private final Supplier<? extends IDynamicRegistry<?, ?>> registry;
    private final ResourceLocation mainModel;
    private final Map<ResourceLocation, ResourceLocation> modelLocations;

    private EposResourceType(Supplier<DynamicRegistry<?, ?>> registry, ResourceLocation mainModel) {
        this.registry = registry;
        this.mainModel = mainModel;
        this.modelLocations = new HashMap<>();
    }

    public ResourceLocation getMainModel() {
        return this.mainModel;
    }

    public <T> List<BakedQuad> getQuads(T obj) {
        return Minecraft.getInstance().getModelManager().getModel(this.mainModel).getQuads(null, null, RANDOM,
                EposModelData.constructModelData(obj == null ? null : this.modelLocations.get(obj.getRegistryName())));
    }

    public void refresh() {
        this.modelLocations.clear();

        this.modelLocations.put(EposRegistries.MISSING_ENTRY, DynamicRegistryBakedModel.MISSING_MODEL_LOCATION);

        IDynamicRegistry<?, ?> registry = this.registry.get();
        if (registry != null) {
            String subdirectory = registry.getName().getPath();
            registry.keySet().forEach(loc -> this.modelLocations.computeIfAbsent(loc,
                    l -> new ResourceLocation(l.getNamespace(), subdirectory + "/" + l.getPath())));
        }
    }

    public Collection<ResourceLocation> getModels() {
        return Collections.unmodifiableCollection(this.modelLocations.values());
    }

    @Override
    public PackType getPackType() {
        return null;
    }

    @Override
    public String getSuffix() {
        return null;
    }

    @Override
    public String getPrefix() {
        return null;
    }
}

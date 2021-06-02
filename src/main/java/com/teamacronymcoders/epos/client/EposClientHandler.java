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

package com.teamacronymcoders.epos.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.teamacronymcoders.epos.api.registry.DynamicRegistry;
import com.teamacronymcoders.epos.client.renderer.model.EposResourceType;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.resource.VanillaResourceType;

public class EposClientHandler {

    private static EposClientHandler instance;
    private final Minecraft mc;
    private final Map<ResourceLocation, EposResourceType> resourceLoaders;

    public EposClientHandler(IEventBus modBus, IEventBus forgeBus) {
        instance = this;
        this.mc = Minecraft.getInstance();
        this.resourceLoaders = new HashMap<>();
    }

    public static final EposClientHandler clientInstance() {
        return instance;
    }

    public void refreshResource(Collection<DynamicRegistry<?, ?>> registries) {
        registries.stream().map(DynamicRegistry::getRegistryName).filter(this.resourceLoaders::containsKey)
                .map(this.resourceLoaders::get).forEach(EposResourceType::refresh);
        ForgeHooksClient.refreshResources(this.mc, VanillaResourceType.MODELS);
    }
}

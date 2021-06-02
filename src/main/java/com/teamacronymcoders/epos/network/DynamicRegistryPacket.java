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

package com.teamacronymcoders.epos.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import com.teamacronymcoders.epos.api.registry.DynamicRegistry;
import com.teamacronymcoders.epos.client.EposClientHandler;
import com.teamacronymcoders.epos.registry.DynamicRegistryHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class DynamicRegistryPacket {

    private Collection<DynamicRegistry<?, ?>> registries;

    public DynamicRegistryPacket(Collection<DynamicRegistry<?, ?>> registries) {
        this.registries = registries;
    }

    public DynamicRegistryPacket(PacketBuffer buffer) {
        int size = buffer.readInt();
        this.registries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DynamicRegistry<?, ?> registry = DynamicRegistryHandler.INSTANCE.getRegistry(buffer.readResourceLocation());
            registry.readRegistry(buffer);
            this.registries.add(registry);
        }
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.registries.size());
        this.registries.forEach(registry -> {
            buffer.writeResourceLocation(registry.getRegistryName());
            registry.writeRegistry(buffer);
        });
    }

    public boolean handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> EposClientHandler.clientInstance().refreshResource(this.registries)));
        return true;
    }
}

package com.teamacronymcoders.epos.api.network.packets.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface Handler<T> {
    void encode(T message, FriendlyByteBuf buffer);
    T decode(FriendlyByteBuf buffer);
    boolean handle(final T message, final Supplier<NetworkEvent.Context> context);
}

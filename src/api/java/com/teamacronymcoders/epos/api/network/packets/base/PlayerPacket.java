package com.teamacronymcoders.epos.api.network.packets.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class PlayerPacket<T extends PlayerPacket<T>> implements Handler<T>{

    private final UUID player;

    public PlayerPacket(Player player) {
        this.player = player.getUUID();
    }

    public PlayerPacket(UUID player) {
        this.player = player;
    }

    @Nullable
    public Player getClientPlayer(T message, Supplier<NetworkEvent.Context> context) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel clientLevel = minecraft.level;
        if (clientLevel != null) {
            return clientLevel.getPlayerByUUID(message.getPlayer());
        }
        return null;
    }

    @Nullable
    public Player getServerPlayer(T message, Supplier<NetworkEvent.Context> context) {
        return context.get().getSender() != null ? context.get().getSender() : null;
    }

    public UUID getPlayer() {
        return player;
    }
}

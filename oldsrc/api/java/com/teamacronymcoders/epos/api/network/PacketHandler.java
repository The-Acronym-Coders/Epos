package com.teamacronymcoders.epos.api.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class PacketHandler {

    public static SimpleChannel INSTANCE;

    public PacketHandler() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("epos", "network"), () -> "1.0", s -> true, s -> true);
        registerMessages();
    }

    public static void registerMessages() {

    }

    public static void sendToPlayer(Object msg, Player player) {
        if (EffectiveSide.get() == LogicalSide.SERVER) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
        }
    }
}

package com.teamacronymcoders.epos.api.network.packets.packets.clientbound;

import com.teamacronymcoders.epos.api.network.packets.base.PlayerPacket;
import com.teamacronymcoders.epos.api.network.packets.packets.clientbound.handlers.LevelDownPacketHandler;
import com.teamacronymcoders.epos.api.network.packets.packets.clientbound.types.TypeEnum;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

public class LevelDownPacket extends PlayerPacket<LevelDownPacket> {

    private final TypeEnum targetType;
    @Nullable
    private final ResourceLocation identifier;
    private final int amount;

    public LevelDownPacket(Player player, TypeEnum targetType, @Nullable ResourceLocation identifier, int amount) {
        super(player);
        this.targetType = targetType;
        this.identifier = identifier;
        this.amount = amount;
    }

    public LevelDownPacket(UUID player, TypeEnum targetType, @Nullable ResourceLocation identifier, int amount) {
        super(player);
        this.targetType = targetType;
        this.identifier = identifier;
        this.amount = amount;
    }

    @Override
    public void encode(LevelDownPacket message, FriendlyByteBuf buffer) {
        buffer.writeUUID(message.getPlayer());
        buffer.writeByte(message.targetType.ordinal());
        boolean isNull = message.identifier == null;
        buffer.writeBoolean(isNull);
        if (!isNull)
            buffer.writeResourceLocation(message.identifier);
        buffer.writeVarInt(message.amount);
    }

    @Override
    public LevelDownPacket decode(FriendlyByteBuf buffer) {
        return new LevelDownPacket(buffer.readUUID(), TypeEnum.values()[buffer.readByte()], buffer.readBoolean() ? buffer.readResourceLocation() : null, buffer.readVarInt());
    }

    @Override
    public boolean handle(LevelDownPacket message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> LevelDownPacketHandler.handlePacket(message)));
        return true;
    }

    public TypeEnum getTargetType() {
        return targetType;
    }

    public ResourceLocation getIdentifier() {
        return identifier;
    }

    public int getAmount() {
        return amount;
    }
}

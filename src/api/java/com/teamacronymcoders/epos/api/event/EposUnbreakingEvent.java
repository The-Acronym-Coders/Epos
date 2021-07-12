package com.teamacronymcoders.epos.api.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EposUnbreakingEvent extends PlayerEvent {

    private final int originalLevel;
    private int modifiedLevel;
    private final ServerPlayerEntity serverPlayer;
    private final ItemStack stack;

    public EposUnbreakingEvent(int originalLevel, ServerPlayerEntity serverPlayer, ItemStack stack) {
        super(serverPlayer);
        this.originalLevel = originalLevel;
        this.modifiedLevel = originalLevel;
        this.serverPlayer = serverPlayer;
        this.stack = stack;
    }

    public int getOriginalLevel() {
        return originalLevel;
    }

    public int getModifiedLevel() {
        return modifiedLevel;
    }

    public void setModifiedLevel(int modifiedLevel) {
        this.modifiedLevel = modifiedLevel;
    }

    public ServerPlayerEntity getServerPlayer() {
        return serverPlayer;
    }

    public ItemStack getStack() {
        return stack;
    }

}

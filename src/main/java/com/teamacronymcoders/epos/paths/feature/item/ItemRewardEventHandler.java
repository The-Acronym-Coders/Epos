package com.teamacronymcoders.epos.paths.feature.item;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;

// TODO: Replace with Capability
// TODO: Also reconsider if this would work well...
public class ItemRewardEventHandler {
    public static void onDeath(PlayerEvent.Clone event) {
        if (event.getOriginal().getPersistentData().contains("item_rewards")) {
            CompoundNBT nbt = event.getOriginal().getPersistentData().getCompound("item_rewards");
            event.getPlayer().getPersistentData().put("item_rewards", nbt);
        }
    }
}

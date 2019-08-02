package com.teamacronymcoders.eposmajorum.pathfeature.item;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemRewardEventHandler {
    public static void onDeath(PlayerEvent.Clone event) {
        if (event.getOriginal().getEntityData().contains("item_rewards")) {
            CompoundNBT nbt = event.getOriginal().getEntityData().getCompound("item_rewards");
            event.getEntityPlayer().getEntityData().put("item_rewards", nbt);
        }
    }
}

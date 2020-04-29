package com.teamacronymcoders.epos.pathfeature.item;

import com.teamacronymcoders.epos.api.EposAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

// TODO: Replace with Capability
@Mod.EventBusSubscriber(modid = EposAPI.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRewardEventHandler {
    public static void onDeath(PlayerEvent.Clone event) {
        if (event.getOriginal().getPersistentData().contains("item_rewards")) {
            CompoundNBT nbt = event.getOriginal().getPersistentData().getCompound("item_rewards");
            event.getPlayer().getPersistentData().put("item_rewards", nbt);
        }
    }
}

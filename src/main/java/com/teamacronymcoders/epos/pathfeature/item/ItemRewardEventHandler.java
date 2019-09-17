package com.teamacronymcoders.epos.pathfeature.item;

import com.teamacronymcoders.epos.api.EposAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

// TODO: Replace with Capability
@Mod.EventBusSubscriber(modid = EposAPI.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRewardEventHandler {
    public static void onDeath(PlayerEvent.Clone event) {
        if (event.getOriginal().getPersistantData().contains("item_rewards")) {
            CompoundNBT nbt = event.getOriginal().getPersistantData().getCompound("item_rewards");
            event.getPlayer().getPersistantData().put("item_rewards", nbt);
        }
    }
}

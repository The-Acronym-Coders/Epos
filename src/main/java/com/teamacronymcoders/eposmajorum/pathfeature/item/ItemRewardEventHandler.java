package com.teamacronymcoders.eposmajorum.pathfeature.item;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EposAPI.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRewardEventHandler {
    public static void onDeath(PlayerEvent.Clone event) {
        if (event.getOriginal().getEntityData().contains("item_rewards")) {
            CompoundNBT nbt = event.getOriginal().getEntityData().getCompound("item_rewards");
            event.getEntityPlayer().getEntityData().put("item_rewards", nbt);
        }
    }
}

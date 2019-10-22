package com.teamacronymcoders.epos.container.character;

import com.hrznstudio.titanium.container.TitaniumContainerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.registries.ObjectHolder;

public class SkillsContainer extends TitaniumContainerBase {
    @ObjectHolder("epos:skills")
    public static ContainerType<SkillsContainer> TYPE;

    public SkillsContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        super(id, inventory, buffer);
    }
}

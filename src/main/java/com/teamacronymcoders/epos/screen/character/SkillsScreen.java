package com.teamacronymcoders.epos.screen.character;

import com.google.common.collect.Lists;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IGuiAddon;
import com.hrznstudio.titanium.client.gui.GuiAddonScreen;
import com.hrznstudio.titanium.client.gui.asset.IAssetProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

import java.util.List;

public class SkillsScreen extends GuiAddonScreen {
    public SkillsScreen() {
        super(IAssetProvider.DEFAULT_PROVIDER, true);
    }

    @Override
    public List<IFactory<IGuiAddon>> guiAddons() {
        return Lists.newArrayList(

        );
    }
}

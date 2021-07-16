package com.teamacronymcoders.epos.client.gui;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.client.screen.ScreenAddonScreen;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.client.gui.addons.PlayerRendererAddon;
import com.teamacronymcoders.epos.client.gui.assets.MainCharacterScreenAssetProvider;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class MainCharacterScreen extends ScreenAddonScreen {

    private final PlayerEntity player;
    private final ICharacterSheet sheet;

    public MainCharacterScreen(PlayerEntity player) {
        super(new MainCharacterScreenAssetProvider(), true);
        this.player = player;
        this.sheet = EposCharacterUtil.getCharacterSheet(player);
    }

    @Override
    public List<IFactory<IScreenAddon>> guiAddons() {
        List<IFactory<IScreenAddon>> addons = new ArrayList<>();
        addons.add(() -> new PlayerRendererAddon(0,0, this.player, 1, true));
        return addons;
    }
}

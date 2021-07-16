package com.teamacronymcoders.epos.client.gui.assets;

import com.hrznstudio.titanium.api.client.GenericAssetType;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IAssetType;

public class EposAssets {

    public static final IAssetType<IAsset> PLAYER_RENDERER_BACKGROUND_ASSET = new GenericAssetType<IAsset>(MainCharacterScreenAssetProvider.CHARACTER_SCREEN_PROVIDER::getAsset, IAsset.class);

}

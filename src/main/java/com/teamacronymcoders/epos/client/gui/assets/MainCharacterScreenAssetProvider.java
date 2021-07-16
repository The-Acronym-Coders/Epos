package com.teamacronymcoders.epos.client.gui.assets;

import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.api.client.IAsset;
import com.hrznstudio.titanium.api.client.IAssetType;
import com.hrznstudio.titanium.api.client.assets.types.IBackgroundAsset;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import com.teamacronymcoders.epos.Epos;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainCharacterScreenAssetProvider implements IAssetProvider {

    public static final MainCharacterScreenAssetProvider CHARACTER_SCREEN_PROVIDER = new MainCharacterScreenAssetProvider();

    private static Map<IAssetType<?>, IAsset> assetMap;
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Epos.ID, "textures/gui/main_character_screen.png");

    private static final IBackgroundAsset BACKGROUND_ASSET = new IBackgroundAsset() {
        @Override
        public Point getInventoryPosition() {
            return null;
        }

        @Override
        public Point getHotbarPosition() {
            return null;
        }

        @Override
        public Rectangle getArea() {
            return null;
        }

        @Override
        public ResourceLocation getResourceLocation() {
            return BACKGROUND_TEXTURE;
        }
    };

    private final IAsset PLAYER_RENDERER_BACKGROUND = new IAsset() {
        @Override
        public Rectangle getArea() {
            return new Rectangle(0, 0, 51, 72);
        }

        @Override
        public ResourceLocation getResourceLocation() {
            return new ResourceLocation(Epos.ID, "textures/gui/addon/player_renderer_background.png");
        }
    };

    public MainCharacterScreenAssetProvider() {
        assetMap = new HashMap<>();
        assetMap.put(AssetTypes.BACKGROUND, BACKGROUND_ASSET);
    }

    @Nullable
    @Override
    public <T extends IAsset> T getAsset(IAssetType<T> assetType) {
        if (assetMap.containsKey(assetType)) {
            return assetType.castOrDefault(assetMap.get(assetType));
        }
        return assetType.getDefaultAsset();
    }


}

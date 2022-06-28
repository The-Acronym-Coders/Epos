package com.teamacronymcoders.epos.client.screen.purchase;

import com.teamacronymcoders.epos.api.path.IPath;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.loading.StringUtils;

import java.util.Comparator;

public class PathPurchaseScreen extends PurchaseScreen<IPath> {

    private static String stripControlCodes(String value) { return net.minecraft.util.StringUtil.stripColor(value); }
    private enum SortingMode implements Comparator<IPath> {
        NORMAL,
        A_TO_Z{ @Override protected int compare(String first, String second) { return first.compareTo(second); }},
        Z_TO_A{ @Override protected int compare(String first, String second) { return second.compareTo(first); }};

        Button button;
        protected int compare(String first, String second) { return 0; }

        @Override
        public int compare(IPath first, IPath second) {
            String firstName = StringUtils.toLowerCase(stripControlCodes(first.getName().toString()));
            String secondName = StringUtils.toLowerCase(stripControlCodes(second.getName().toString()));
            return compare(firstName, secondName);
        }

        Component getButtonText() {
            return Component.translatable("epos.menu.purchase.path." + StringUtils.toLowerCase(name()));
        }
    }

    protected PathPurchaseScreen(Player player, Screen parentScreen) {
        super(player, parentScreen, "path");
    }

}

package com.teamacronymcoders.epos.client.screen.purchase;

import com.teamacronymcoders.epos.api.IDescribable;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.client.widget.scroll.InfoPanel;
import com.teamacronymcoders.epos.client.widget.scroll.ScrollableListWidget;
import com.teamacronymcoders.epos.client.widget.scroll.TypeEntry;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmlclient.gui.widget.ModListWidget;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class PurchaseScreen<T extends IDescribable> extends Screen {

    private Player player;
    private Screen parentScreen;

    private ScrollableListWidget entryList;
    private InfoPanel info;
    private TypeEntry<T> selectedEntry = null;
    private int listWidth;
    private List<T> entries;
    private final List<T> unsortedEntries;
    private Button returnButton, purchaseButton;


    private int buttonMargin = 1;
    private String lastFilterText = "";

    private EditBox searchField;

    protected PurchaseScreen(Player player, Screen parentScreen, String identifier) {
        super(new TranslatableComponent("epos.menu." + identifier + ".title"));
        List<T> unsortedEntries1;
        this.player = player;
        this.parentScreen = parentScreen;
        if (EposCharacterUtil.getCharacterSheet(player) != null) {
            ICharacterSheet sheet = EposCharacterUtil.getCharacterSheet(player);
            List<T> entryList = new ArrayList<>();

            this.entries = null;
            unsortedEntries1 = entries;
        }
        unsortedEntries1 = new ArrayList<>();
        this.unsortedEntries = unsortedEntries1;
    }



}

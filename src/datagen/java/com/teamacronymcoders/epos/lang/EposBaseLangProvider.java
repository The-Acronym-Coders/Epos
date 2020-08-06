package com.teamacronymcoders.epos.lang;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.IFeat;
import com.teamacronymcoders.epos.api.path.IPath;
import com.teamacronymcoders.epos.api.skill.ISkill;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class EposBaseLangProvider extends LanguageProvider {

    public EposBaseLangProvider(DataGenerator generator, String locale) {
        super(generator, EposAPI.ID, locale);
    }

    @Override
    protected void addTranslations() {}

    /**
     * Helper function to add advancements to the lang generator
     *
     * @param advancement The advancement for localizations to be added
     * @param title       The title of the advancement
     * @param description The description of the advancement
     */
    public void add(Advancement advancement, String title, String description) {
        final DisplayInfo display = advancement.getDisplay();
        add(display.getTitle().getUnformattedComponentText(), title);
        add(display.getDescription().getUnformattedComponentText(), description);
    }

    /**
     * Helper function to add 'Path' to the lang generator
     *
     * @param path The path to be localized.
     * @param translation The translation.
     */
    public void add(IPath path, String translation) {
        add(path.getRegistryName().toString(), translation);
    }

    /**
     * Helper function to add 'Path' to the lang generator
     *
     * @param skill The skill to be localized.
     * @param translation The translation.
     */
    public void add(ISkill skill, String translation) {
        add(skill.getRegistryName().toString(), translation);
    }

    /**
     * Helper function to add 'Path' to the lang generator
     *
     * @param feat The feat to be localized.
     * @param translation The translation.
     */
    public void add(IFeat feat, String translation) {
        add(feat.getRegistryName().toString(), translation);
    }

}

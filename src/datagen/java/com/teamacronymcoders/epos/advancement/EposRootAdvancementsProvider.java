package com.teamacronymcoders.epos.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Consumer;

public class EposRootAdvancementsProvider extends ExtendableAdvancementProvider {

    private static Advancement core;

    public EposRootAdvancementsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        core = Advancement.Builder.builder()
            .withDisplay(
                getDefaultIcon(),
                new TranslationTextComponent("advancements.epos.core.root.title"),
                new TranslationTextComponent("advancements.epos.core.root.description"),
                new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/stone.png"),
                FrameType.CHALLENGE, false, false, false
            )
            .withRequirementsStrategy(IRequirementsStrategy.OR)
            .withCriterion("crafting_table", InventoryChangeTrigger.Instance.forItems(Blocks.CRAFTING_TABLE))
            .withCriterion("killed", KilledTrigger.Instance.playerKilledEntity())
            .withCriterion("killed_by", KilledTrigger.Instance.entityKilledPlayer())
            .register(consumer, "epos:core/root");
    }

    @Override
    public String getName() {
        return "Epos Advancements: [Core]";
    }

    public static Advancement getCore() {
        return core;
    }
}

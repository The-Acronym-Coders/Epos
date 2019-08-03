package com.teamacronymcoders.eposmajorum.content.feats.utility;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

//TODO: Implement using a map for leaves > sapling values once the API makes it possible
public class GraftlingCertaintyFeat {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "graftling_certainty");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(BlockEvent.HarvestDropsEvent.class,
                            (harvestDropsEvent, entity, iCharacterStats) -> {
                                if (harvestDropsEvent.getState().getBlock() instanceof LeavesBlock && !harvestDropsEvent.isSilkTouching()) {
                                    LeavesBlock block = (LeavesBlock) harvestDropsEvent.getState().getBlock();
                                    boolean containsSapling = false;
                                    List<ItemStack> stacks = harvestDropsEvent.getDrops();
                                    for (ItemStack stack : stacks) {
                                        if (stack.getItem().isIn(ItemTags.SAPLINGS)) {
                                            containsSapling = true;
                                        }
                                    }
                                    if (block.isIn(BlockTags.LEAVES) && !containsSapling) {
                                        LootContext.Builder context = new LootContext.Builder(entity.world.getServer().getWorld(entity.dimension));
                                        block.getDrops(harvestDropsEvent.getState(), context);

                                    }

                                    harvestDropsEvent.setDropChance(1f);
                                }
                            }).finish();
}

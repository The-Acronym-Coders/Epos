package com.teamacronymcoders.eposmajorum.content;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GluttonousHunger {
    public static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "gluttonous_hunger");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                .withEventHandler(TickEvent.PlayerTickEvent.class, (playerTickEvent, livingEntity, iCharacterStats) -> {
                    PlayerEntity playerEntity = playerTickEvent.player;
                    if (playerEntity == null || playerEntity.isCreative() || playerEntity.isSpectator()) {
                        return;
                    } else {
                        int playerHunger = playerEntity.getFoodStats().getFoodLevel();
                        if (playerHunger < 20) {
                            NonNullList<ItemStack> inventoryList = playerEntity.inventory.mainInventory;
                            ItemStack currentStack = ItemStack.EMPTY;
                            int hungerNeeded = 20 - playerHunger;
                            int bestHungerPoints = 0;

                            for (ItemStack stack : inventoryList) {
                                if (!stack.isEmpty() && stack.getItem().isFood()) {
                                    int hungerPoints = stack.getItem().getFood().getHealing();
                                    if (currentStack.isEmpty() || hungerPoints < bestHungerPoints && hungerPoints >= hungerNeeded ||
                                            hungerPoints > bestHungerPoints && bestHungerPoints < hungerNeeded) {
                                        //No food item yet OR
                                        //The food is less filling but will still make you full OR
                                        //The current piece won't fill you and this piece will fill you more
                                        currentStack = stack;
                                        bestHungerPoints = hungerPoints;
                                        if (bestHungerPoints == hungerNeeded) {
                                            //Fills you up entirely, with no hunger wasted. Best item given we don't use saturation yet
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!currentStack.isEmpty()) {
                                currentStack.getItem().onItemUseFinish(currentStack, playerEntity.getEntityWorld(), playerEntity);
                            }
                    }

                }}).finish();
}

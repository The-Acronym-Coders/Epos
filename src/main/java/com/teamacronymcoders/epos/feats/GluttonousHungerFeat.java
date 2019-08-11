package com.teamacronymcoders.epos.feats;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.feat.Feat;
import com.teamacronymcoders.epos.api.feat.FeatBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.TickEvent;

public class GluttonousHungerFeat {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "gluttonous_hunger");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(TickEvent.PlayerTickEvent.class, (playerTickEvent, livingEntity, iCharacterStats) -> {
                        PlayerEntity playerEntity = playerTickEvent.player;
                        if (eventChecks(playerEntity)) {
                            int playerHunger = playerEntity.getFoodStats().getFoodLevel();
                            if (playerHunger < 20) {
                                NonNullList<ItemStack> inventoryList = playerEntity.inventory.mainInventory;
                                ItemStack currentStack = ItemStack.EMPTY;
                                int hungerNeeded = 20 - playerHunger;
                                int bestHungerPoints = 0;

                                for (ItemStack stack : inventoryList) {
                                    if (!stack.isEmpty() && stack.getItem().isFood()) {
                                        Food food = stack.getItem().getFood();
                                        int hungerPoints = food != null ? food.getHealing() : 0;
                                        if (currentStack.isEmpty() ||
                                                hungerPoints < bestHungerPoints && hungerPoints >= hungerNeeded ||
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

                        }
                    }).finish();

    public static boolean eventChecks(PlayerEntity playerEntity) {
        return playerEntity != null && !playerEntity.isCreative() && !playerEntity.isSpectator();
    }
}

package com.teamacronymcoders.eposmajorum.content.utility;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatAcquiredEvent;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import com.teamacronymcoders.eposmajorum.api.skill.SkillInfo;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/**
 * You might look at this in the future and look at the "Math.max()" call and wonder "Why?"
 * Well I'll leave this Quote from the Official Minecraft Wiki to explain why we have to have a Max of 5 on the "Lure" enchantment.
 * <p>
 * Lure increases the rate of a fish biting the player's hook while fishing. Specifically, it decreases the wait time for a fish to appear (by 5 seconds per level).
 * If commands are used to increase the enchantment level beyond 5, the wait timer does not start and fish do not bite.
 */
public class LuckyAnglerSkill {
    private static final ResourceLocation NAME = new ResourceLocation(EposAPI.ID, "lucky_angler");
    public static final Feat FEAT =
            FeatBuilder.start(NAME)
                    .withEventHandler(EntityJoinWorldEvent.class,
                            (entityJoinWorldEvent, entity, iCharacterStats) -> {
                                if (entityJoinWorldEvent.getEntity() instanceof FishingBobberEntity) {
                                    SkillInfo skill = iCharacterStats.getSkills().get(NAME.toString());
                                    FishingBobberEntity oldEntity = (FishingBobberEntity) entityJoinWorldEvent.getEntity();
                                    if (!oldEntity.getEntityData().contains("replaced") && oldEntity.getAngler() != null && skill != null) {
                                        ItemStack stack = oldEntity.getAngler().getActiveItemStack();
                                        FishingBobberEntity newEntity = new FishingBobberEntity(oldEntity.getAngler(),
                                                oldEntity.world,
                                                EnchantmentHelper.getFishingLuckBonus(stack) + skill.getLevel(),
                                                Math.max(5, EnchantmentHelper.getFishingSpeedBonus(stack) + skill.getLevel()));
                                        newEntity.getEntityData().putBoolean("replaced", true);
                                        entityJoinWorldEvent.getWorld().addEntity(newEntity);
                                        entityJoinWorldEvent.setCanceled(true);
                                    }
                                }
                            })
                    .withEventHandler(FeatAcquiredEvent.class,
                            (featAcquiredEvent, entity, iCharacterStats) -> {
                                if (featAcquiredEvent.getFeatAcquired().getRegistryName().compareTo(NAME) == 0) {
                                    iCharacterStats.getSkills().putSkill(NAME);
                                }
                            }).finish();
}
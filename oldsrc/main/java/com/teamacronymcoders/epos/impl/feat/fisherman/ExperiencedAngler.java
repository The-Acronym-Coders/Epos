package com.teamacronymcoders.epos.impl.feat.fisherman;

import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.impl.skill.EposSkillIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.annotation.Nonnull;

public class ExperiencedAngler {
    public static final EventManager.ISubscribe featManager = EventManager.create(EntityJoinWorldEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                if (event.getEntity() instanceof FishingHook) {
                    FishingHook hook = (FishingHook) event.getEntity();
                    Player player = hook.getPlayerOwner();
                    if (player != null) {
                        return EposCharacterUtil.hasFeat(player, EposFeatIds.EXPERIENCED_ANGLER) && EposCharacterUtil.hasSkill(player, EposSkillIds.FISHING);
                    }
                }
                return false;
            })
            .process(event -> {
                FishingHook fishingBobber = (FishingHook) event.getEntity();
                Player player = fishingBobber.getPlayerOwner();
                if (!fishingBobber.getPersistentData().contains("replaced")) {
                    int luckModifier = getLuckModifier(player);
                    ItemStack stack = player.getMainHandItem();
                    FishingHook newFishingBobber = new FishingHook(player, fishingBobber.level, EnchantmentHelper.getFishingLuckBonus(stack) + luckModifier, Math.min(5, EnchantmentHelper.getFishingSpeedBonus(stack) + luckModifier)); // adding luck to speed bonus?
                    newFishingBobber.getPersistentData().putBoolean("replaced", true);
                    event.getWorld().addFreshEntity(newFishingBobber);
                    event.setCanceled(true);
                }
            });

    private static int getLuckModifier(@Nonnull Player character) {
        SkillInfo info = EposCharacterUtil.getCharacterSheet(character).getSkills().getOrCreate(EposFeatIds.EXPERIENCED_ANGLER);
        int levelsPerModifierTier = Math.round(info.getMaxLevel() / 3f);
        return info.getLevel() / levelsPerModifierTier < 1 ? 0 : Math.round((float) info.getLevel() / (float) levelsPerModifierTier);
    }
}

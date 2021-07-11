package com.teamacronymcoders.epos.impl.feat.fisherman;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.Epos;
import com.teamacronymcoders.epos.api.skill.SkillInfo;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.annotation.Nonnull;

public class ExperiencedAngler {
    public static final EventManager.ISubscribe featManager = EventManager.create(EntityJoinWorldEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                if (event.getEntity() instanceof FishingBobberEntity) {
                    FishingBobberEntity fishingBobber = (FishingBobberEntity) event.getEntity();
                    PlayerEntity player = fishingBobber.getPlayerOwner();
                    if (player != null) {
                        return EposCharacterUtil.hasFeat(player, new ResourceLocation(Epos.ID, "rapid_angler")) && EposCharacterUtil.hasSkill(player, new ResourceLocation(Epos.ID, "fishing"));
                    }
                }
                return false;
            })
            .process(event -> {
                FishingBobberEntity fishingBobber = (FishingBobberEntity) event.getEntity();
                PlayerEntity player = fishingBobber.getPlayerOwner();
                if (!fishingBobber.getPersistentData().contains("replaced")) {
                    int luckModifier = getLuckModifier(player);
                    ItemStack stack = player.getMainHandItem();
                    FishingBobberEntity newFishingBobber = new FishingBobberEntity(player, fishingBobber.level, EnchantmentHelper.getFishingLuckBonus(stack) + luckModifier, Math.min(5, EnchantmentHelper.getFishingSpeedBonus(stack) + luckModifier)); // adding luck to speed bonus?
                    newFishingBobber.getPersistentData().putBoolean("replaced", true);
                    event.getWorld().addFreshEntity(newFishingBobber);
                    event.setCanceled(true);
                }
            });

    private static int getLuckModifier(@Nonnull PlayerEntity character) {
        SkillInfo info = EposCharacterUtil.getCharacterSheet(character).getSkills().getOrCreate(new ResourceLocation(Epos.ID, "rapid_angler"));
        int levelsPerModifierTier = Math.round(info.getMaxLevel() / 3f);
        return info.getLevel() / levelsPerModifierTier < 1 ? 0 : Math.round((float) info.getLevel() / (float) levelsPerModifierTier);
    }
}

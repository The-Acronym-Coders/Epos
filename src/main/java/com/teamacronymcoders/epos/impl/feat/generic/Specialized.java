package com.teamacronymcoders.epos.impl.feat.generic;

import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.event.EposUnbreakingEvent;
import com.teamacronymcoders.epos.api.event.eventhandler.EventManager;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;

import java.util.Arrays;

public class Specialized {

    public static final EventManager.ISubscribe featManager = EventManager.create(EposUnbreakingEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                ServerPlayer playerEntity = event.getServerPlayer();
                ItemStack stack = event.getStack();
                boolean hasMatchingSpecialization = false;
                if (ToolActions.DEFAULT_AXE_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.AXE_SPECIALIZATION_NOVICE, EposFeatIds.AXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.AXE_SPECIALIZATION_ADVANCED);
                } else if (ToolActions.DEFAULT_HOE_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.HOE_SPECIALIZATION_NOVICE, EposFeatIds.HOE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.HOE_SPECIALIZATION_ADVANCED);
                } else if (ToolActions.DEFAULT_PICKAXE_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.PICKAXE_SPECIALIZATION_NOVICE, EposFeatIds.PICKAXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.PICKAXE_SPECIALIZATION_ADVANCED);
                } else if (ToolActions.DEFAULT_SHOVEL_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.SHOVEL_SPECIALIZATION_NOVICE, EposFeatIds.SHOVEL_SPECIALIZATION_INTERMEDIATE, EposFeatIds.SHOVEL_SPECIALIZATION_ADVANCED);
                }
                return playerEntity != null && hasMatchingSpecialization;
            })
            .process(event -> {
                ServerPlayer playerEntity = event.getServerPlayer();
                ItemStack stack = event.getStack();
                int unbreakingModifier = 0;
                if (ToolActions.DEFAULT_AXE_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.AXE_SPECIALIZATION_NOVICE, EposFeatIds.AXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.AXE_SPECIALIZATION_ADVANCED);
                }

                if (ToolActions.DEFAULT_HOE_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.HOE_SPECIALIZATION_NOVICE, EposFeatIds.HOE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.HOE_SPECIALIZATION_ADVANCED);
                }

                if (ToolActions.DEFAULT_PICKAXE_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.PICKAXE_SPECIALIZATION_NOVICE, EposFeatIds.PICKAXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.PICKAXE_SPECIALIZATION_ADVANCED);
                }

                if (ToolActions.DEFAULT_SHOVEL_ACTIONS.stream().anyMatch(stack::canPerformAction)) {
                    unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.SHOVEL_SPECIALIZATION_NOVICE, EposFeatIds.SHOVEL_SPECIALIZATION_INTERMEDIATE, EposFeatIds.SHOVEL_SPECIALIZATION_ADVANCED);
                }

                int original = event.getOriginalLevel();
                event.setModifiedLevel(original + unbreakingModifier);
            });

    private static int getModifierAmount(ServerPlayer character, ResourceLocation... featIds) {
        ICharacterSheet sheet = EposCharacterUtil.getCharacterSheet(character);
        if (sheet != null) {
            int unbreakingAmount = 0;
            unbreakingAmount += Arrays.stream(featIds).filter(id -> sheet.getFeats().getOrCreate(id).isUnlocked()).count();
            return unbreakingAmount;
        }
        return 0;
    }
}

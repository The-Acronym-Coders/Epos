package com.teamacronymcoders.epos.impl.feat.generic;

import com.hrznstudio.titanium.event.handler.EventManager;
import com.teamacronymcoders.epos.api.character.ICharacterSheet;
import com.teamacronymcoders.epos.api.event.EposUnbreakingEvent;
import com.teamacronymcoders.epos.impl.feat.EposFeatIds;
import com.teamacronymcoders.epos.util.EposCharacterUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Arrays;
import java.util.Set;

public class Specialized {
    public static final EventManager.ISubscribe featManager = EventManager.create(EposUnbreakingEvent.class, EventManager.Bus.FORGE)
            .filter(event -> {
                ServerPlayerEntity playerEntity = event.getServerPlayer();
                Set<ToolType> toolTypes = event.getStack().getToolTypes();
                boolean hasMatchingSpecialization = false;
                for (ToolType type : toolTypes) {
                    String name = type.getName();
                    switch (name) {
                        case "axe":
                            hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.AXE_SPECIALIZATION_NOVICE, EposFeatIds.AXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.AXE_SPECIALIZATION_ADVANCED);
                            break;
                        case "hoe":
                            hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.HOE_SPECIALIZATION_NOVICE, EposFeatIds.HOE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.HOE_SPECIALIZATION_ADVANCED);
                            break;
                        case "pickaxe":
                            hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.PICKAXE_SPECIALIZATION_NOVICE, EposFeatIds.PICKAXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.PICKAXE_SPECIALIZATION_ADVANCED);
                            break;
                        case "shovel":
                            hasMatchingSpecialization = EposCharacterUtil.anyFeatsAcquired(playerEntity, EposFeatIds.SHOVEL_SPECIALIZATION_NOVICE, EposFeatIds.SHOVEL_SPECIALIZATION_INTERMEDIATE, EposFeatIds.SHOVEL_SPECIALIZATION_ADVANCED);
                            break;
                    }
                    if (hasMatchingSpecialization) break;
                }
                return playerEntity != null && hasMatchingSpecialization;
            })
            .process(event -> {
                ServerPlayerEntity playerEntity = event.getServerPlayer();
                Set<ToolType> toolTypes = event.getStack().getToolTypes();
                int unbreakingModifier = 0;
                for (ToolType type : toolTypes) {
                    String name = type.getName();
                    switch (name) {
                        case "axe":
                            unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.AXE_SPECIALIZATION_NOVICE, EposFeatIds.AXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.AXE_SPECIALIZATION_ADVANCED);
                            break;
                        case "hoe":
                            unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.HOE_SPECIALIZATION_NOVICE, EposFeatIds.HOE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.HOE_SPECIALIZATION_ADVANCED);
                            break;
                        case "pickaxe":
                            unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.PICKAXE_SPECIALIZATION_NOVICE, EposFeatIds.PICKAXE_SPECIALIZATION_INTERMEDIATE, EposFeatIds.PICKAXE_SPECIALIZATION_ADVANCED);
                            break;
                        case "shovel":
                            unbreakingModifier += getModifierAmount(playerEntity, EposFeatIds.SHOVEL_SPECIALIZATION_NOVICE, EposFeatIds.SHOVEL_SPECIALIZATION_INTERMEDIATE, EposFeatIds.SHOVEL_SPECIALIZATION_ADVANCED);
                            break;
                    }
                }
                int original = event.getOriginalLevel();
                event.setModifiedLevel(original + unbreakingModifier);
            });

    private static int getModifierAmount(ServerPlayerEntity character, ResourceLocation... featIds) {
        ICharacterSheet sheet = EposCharacterUtil.getCharacterSheet(character);
        if (sheet != null) {
            int unbreakingAmount = 0;
            unbreakingAmount += Arrays.stream(featIds).filter(id -> sheet.getFeats().getOrCreate(id).isUnlocked()).count();
            return unbreakingAmount;
        }
        return 0;
    }
}

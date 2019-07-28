package com.teamacronymcoders.eposmajorum.content.miner;

import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.feat.Feat;
import com.teamacronymcoders.eposmajorum.api.feat.FeatBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;

public class CarefulOrLuckyFeat {
    private static final ResourceLocation RL = new ResourceLocation(EposAPI.ID, "gifted_or_fortunate");
    private static String value;
    private static final String careful = "careful";
    private static final String lucky = "lucky";
    private static final Feat FEAT = FeatBuilder.start(RL)
            .withEventHandler(BlockEvent.HarvestDropsEvent.class,
                             (harvestDropsEvent, entity, iCharacterStats) -> {
                if (value.equals(lucky)) {
                    harvestDropsEvent.getFortuneLevel()
                } else if (value.equals(careful)) {
                    harvestDropsEvent.isSilkTouching() = true;
                }
            })

    public static String getValue() {
        return value;
    }

    public static void setValue(String value) {
        CarefulOrLuckyFeat.value = value;
    }
}

package com.teamacronymcoders.epos.impl.feat;

import com.teamacronymcoders.epos.Epos;
import net.minecraft.util.ResourceLocation;

public class EposFeatIds {
    // Feats
    public static final ResourceLocation AGILE_COMBATANT = create("agile_combatant");
    public static final ResourceLocation CASCADING_EXCAVATIONS = create("cascading_excavations");
    public static final ResourceLocation EFFECTIVE_MINING = create("effective_mining");
    public static final ResourceLocation EMBRACE_OF_THE_LOTUS = create("embrace_of_the_lotus");
    public static final ResourceLocation EXPERIENCED_ANGLER = create("experienced_angler");
    public static final ResourceLocation FIST_OF_CRUMBLING_EARTH = create("fist_of_crumbling_earth");
    public static final ResourceLocation FIST_OF_DENSE_EARTH = create("fist_of_dense_earth");
    public static final ResourceLocation MANS_BEST_FRIEND = create("mans_best_friend");
    public static final ResourceLocation PACK_MENTALITY = create("pack_mentality");
    public static final ResourceLocation SPIRIT_OF_BATTLE = create("spirit_of_battle");
    public static final ResourceLocation TIMBER = create("timber");

    // Specialized
    /// Axe
    public static final ResourceLocation AXE_SPECIALIZATION_NOVICE = create("axe_specialization_novice");
    public static final ResourceLocation AXE_SPECIALIZATION_INTERMEDIATE = create("axe_specialization_intermediate");
    public static final ResourceLocation AXE_SPECIALIZATION_ADVANCED = create("axe_specialization_advanced");

    /// Hoe
    public static final ResourceLocation HOE_SPECIALIZATION_NOVICE = create("hoe_specialization_novice");
    public static final ResourceLocation HOE_SPECIALIZATION_INTERMEDIATE = create("hoe_specialization_intermediate");
    public static final ResourceLocation HOE_SPECIALIZATION_ADVANCED = create("hoe_specialization_advanced");

    /// Pickaxe
    public static final ResourceLocation PICKAXE_SPECIALIZATION_NOVICE = create("pickaxe_specialization_novice");
    public static final ResourceLocation PICKAXE_SPECIALIZATION_INTERMEDIATE = create("pickaxe_specialization_intermediate");
    public static final ResourceLocation PICKAXE_SPECIALIZATION_ADVANCED = create("pickaxe_specialization_advanced");

    /// Shovel
    public static final ResourceLocation SHOVEL_SPECIALIZATION_NOVICE = create("shovel_specialization_novice");
    public static final ResourceLocation SHOVEL_SPECIALIZATION_INTERMEDIATE = create("shovel_specialization_intermediate");
    public static final ResourceLocation SHOVEL_SPECIALIZATION_ADVANCED = create("shovel_specialization_advanced");

    public static ResourceLocation create(String name) {
        return new ResourceLocation(Epos.ID, name);
    }
}

package com.teamacronymcoders.epos.api.locks;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.StringNBT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test NBTLockKey NBT comparison")
class NBTLockKeyTest {

    @Nullable
    private CompoundNBT getTagFromJson(String json) {
        try {
            return JsonToNBT.getTagFromJson(json);
        } catch (CommandSyntaxException ignored) {
        }
        return null;
    }

    @Test
    @DisplayName("Test NBTLockKey when given the same compound, simple.")
    void matchesSimple() {
        INBT tag = getTagFromJson("{test: \"value\"}");
        Assertions.assertTrue(NBTLockKeyTestHelper.isNBTSimilar(tag, tag));
    }

    @Test
    @DisplayName("Test NBTLockKey nbt compare check using something other than compounds.")
    void compareString() {
        INBT tag = StringNBT.valueOf("test");
        Assertions.assertTrue(NBTLockKeyTestHelper.isNBTSimilar(tag, tag));
    }

    @Test
    @DisplayName("Test NBTLockKey nbt contains partial int list.")
    void containsPartialIntList() {
        INBT full = new IntArrayNBT(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        INBT partial = new IntArrayNBT(new int[]{1, 3, 5, 8, 9});
        Assertions.assertTrue(NBTLockKeyTestHelper.isNBTSimilar(full, partial));
    }

    @Test
    @DisplayName("Test NBTLockKey nbt contains partial int list, with repeated elements.")
    void containsPartialIntListRepeats() {
        INBT full = new IntArrayNBT(new int[]{1, 1, 1, 2, 3});
        INBT partial = new IntArrayNBT(new int[]{1, 2, 1});
        Assertions.assertTrue(NBTLockKeyTestHelper.isNBTSimilar(full, partial));
        INBT random = new IntArrayNBT(new int[]{1, 2, 3, 4, 5});
        Assertions.assertFalse(NBTLockKeyTestHelper.isNBTSimilar(full, random));
    }

    @Test
    @DisplayName("Test NBTLockKey nbt containing partial match nbt compound.")
    void containsPartialCompound() {
        INBT full = getTagFromJson("{Random: \"test\", Special: {Categories: [\"aoe\", \"harvest\", \"tool\"]}}");
        INBT partial = getTagFromJson("{Special: {Categories: [\"tool\"]}}");
        Assertions.assertTrue(NBTLockKeyTestHelper.isNBTSimilar(full, partial));
    }

    @Test
    @DisplayName("Test NBTLockKey when given the same complex compound.")
    void matchesComplex() {
        //Tag taken from a random 1.12 tinker's construct pickaxe.
        INBT tag = getTagFromJson("{StatsOriginal: {AttackSpeedMultiplier: 1.0f, MiningSpeed: 3.5f, FreeModifiers: 3, Durability: 1122, HarvestLevel: 4, "
                                  + "Attack: 3.6f}, Stats: {AttackSpeedMultiplier: 1.0f, MiningSpeed: 3.5f, FreeModifiers: 3, Durability: 1122, "
                                  + "HarvestLevel: 4, Attack: 3.6f}, Special: {Categories: [\"aoe\", \"harvest\", \"tool\"]}, TinkerData: "
                                  + "{Materials: [\"dark_steel\", \"ardite\", \"obsidian\"], Modifiers: [\"toolleveling\"]}, Modifiers: "
                                  + "[{identifier: \"unnatural\", color: -9671572, level: 1}, {identifier: \"stonebound\", color: -3063280, level: 1}, "
                                  + "{identifier: \"duritos\", color: -10478396, level: 1}, {identifier: \"toolleveling\", color: 16777215, level: 1}], "
                                  + "Traits: [\"unnatural\", \"stonebound\", \"duritos\", \"toolleveling\"]}");
        Assertions.assertTrue(NBTLockKeyTestHelper.isNBTSimilar(tag, tag));
    }
}
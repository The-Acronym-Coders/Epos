package com.teamacronymcoders.epos.classes.feature.item;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.path.feature.IClassFeature;
import com.teamacronymcoders.epos.api.path.feature.CharacterClassFeatureProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

// TODO: This requires clean-up, preferably with Sulfuric Acid
public class ItemRewardFeatureProvider extends CharacterClassFeatureProvider {
    @Override
    public IClassFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String identifier = JSONUtils.getString(jsonObject, "identifier");
        String item = JsonUtils.getString(jsonObject, "item");
        int quantity = JsonUtils.getInt(jsonObject, "quantity");
        CompoundNBT nbt = net.minecraftforge.common.util.JsonUtils.readNBT(jsonObject, "nbt");
        ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)), quantity, nbt);
        if (!identifier.isEmpty() && !stack.isEmpty()) {
            return new ItemRewardFeature(identifier, stack);
        } else {
            throw new JsonParseException("No Identifier found OR No Valid ItemStack created: " + identifier + item);
        }
    }
}

package com.teamacronymcoders.epos.pathfeature.item;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.json.JsonUtils;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeature;
import com.teamacronymcoders.epos.api.pathfeature.IPathFeatureProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

// TODO: Import NBT-Code from BASE to not use MC's Horrid NBT Code
public class ItemRewardFeatureProvider implements IPathFeatureProvider {
    private final ResourceLocation registryName = new ResourceLocation(EposAPI.ID, "item_reward");

    @Override
    public IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
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

    @Nonnull
    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}

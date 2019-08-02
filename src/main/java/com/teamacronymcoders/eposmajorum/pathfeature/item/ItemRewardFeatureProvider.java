package com.teamacronymcoders.eposmajorum.pathfeature.item;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.eposmajorum.api.EposAPI;
import com.teamacronymcoders.eposmajorum.api.json.JsonUtils;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeature;
import com.teamacronymcoders.eposmajorum.api.pathfeature.IPathFeatureProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

import static net.minecraftforge.common.util.JsonUtils.readNBT;

public class ItemRewardFeatureProvider implements IPathFeatureProvider {
    private final ResourceLocation registryName = new ResourceLocation(EposAPI.ID, "item_reward");

    @Override
    public IPathFeature provide(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String identifier = JSONUtils.getString(jsonObject, "identifier");
        String item = JsonUtils.getString(jsonObject,"item");
        int quantity = JsonUtils.getInt(jsonObject, "quantity");
        CompoundNBT nbt = readNBT(jsonObject, "nbt");
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

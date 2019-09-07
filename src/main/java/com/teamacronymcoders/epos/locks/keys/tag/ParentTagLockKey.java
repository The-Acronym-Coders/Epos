package com.teamacronymcoders.epos.locks.keys.tag;

import com.teamacronymcoders.epos.api.EposAPI;
import com.teamacronymcoders.epos.api.locks.keys.IParentLockKey;
import com.teamacronymcoders.epos.api.requirements.IRequirement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class ParentTagLockKey implements IParentLockKey {

    @Nonnull
    private final Collection<ResourceLocation> tags;
    @Nullable
    private final CompoundNBT nbt;

    private ParentTagLockKey(@Nonnull Collection<ResourceLocation> tags, @Nullable CompoundNBT nbt) {
        this.tags = tags;
        this.nbt = nbt == null || nbt.isEmpty() ? null : nbt;
    }

    @Nullable
    public static ParentTagLockKey fromObject(@Nonnull Object object) {
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return null;
            }
            return fromTags(stack.getItem().getTags(), stack.getTag());
        } else if (object instanceof Item) {
            return fromTags(((Item) object).getTags());
        } else if (object instanceof BlockState) {
            return fromTags(((BlockState) object).getBlock().getTags());
        } else if (object instanceof Block) {
            return fromTags(((Block) object).getTags());
        } else if (object instanceof FluidStack) {
            FluidStack stack = (FluidStack) object;
            return fromTags(stack.getFluid().getTags(), stack.getTag());
        } else if (object instanceof Fluid) {
            return fromTags(((Fluid) object).getTags());
        } else if (object instanceof FluidState) {
            return fromTags(((FluidState) object).getFluid().getTags());
        } else if (object instanceof Entity) {
            return fromTags(((Entity) object).getType().getTags());
        } else if (object instanceof EntityType<?>) {
            return fromTags(((EntityType<?>) object).getTags());
        }
        return null;
    }

    @Nullable
    private static ParentTagLockKey fromTags(@Nonnull Collection<ResourceLocation> tags) {
        return fromTags(tags, null);
    }

    @Nullable
    private static ParentTagLockKey fromTags(@Nonnull Collection<ResourceLocation> tags, @Nullable CompoundNBT nbt) {
        return tags.isEmpty() ? null : new ParentTagLockKey(tags, nbt);
    }

    @Nonnull
    @Override
    public List<IRequirement> getSubRequirements() {
        List<IRequirement> requirements = new ArrayList<>();
        for (ResourceLocation location : tags) {
            requirements.addAll(EposAPI.LOCK_REGISTRY.getFuzzyRequirements(new TagLockKey(location, nbt)));
        }
        return requirements;
    }
}
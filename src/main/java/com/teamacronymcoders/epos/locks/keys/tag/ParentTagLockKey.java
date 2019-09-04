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
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

//TODO: Javadoc, this is basically a wrapper for TagLockKey to be able to get the requirements of all the different tags on an item/block/etc
public class ParentTagLockKey implements IParentLockKey {

    @Nonnull
    private final Collection<ResourceLocation> tags;
    @Nullable
    private final CompoundNBT nbt;

    public ParentTagLockKey(@Nonnull Collection<ResourceLocation> tags) {
        this(tags, null);
    }

    public ParentTagLockKey(@Nonnull Collection<ResourceLocation> tags, @Nullable CompoundNBT nbt) {
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
            return fromItem(stack.getItem(), stack.getTag());
        } else if (object instanceof Item) {
            return fromItem((Item) object, null);
        } else if (object instanceof BlockState) {
            return fromTags(((BlockState) object).getBlock().getTags());
        } else if (object instanceof Block) {
            return fromTags(((Block) object).getTags());
        } else if (object instanceof FluidStack) {
            return fromTags((((FluidStack) object).getFluid()).getTags());
        } else if (object instanceof Fluid) {
            return fromTags(((Fluid) object).getTags());
        } else if (object instanceof FluidState) {
            return fromTags(((FluidState) object).getFluid().getTags());
        }
        return null;
    }

    @Nullable
    private static ParentTagLockKey fromItem(@Nonnull Item item, @Nullable CompoundNBT nbt) {
        Collection<ResourceLocation> tags = item.getTags();
        return tags.isEmpty() ? null : new ParentTagLockKey(tags, nbt);
    }

    @Nullable
    private static ParentTagLockKey fromTags(@Nonnull Collection<ResourceLocation> tags) {
        return tags.isEmpty() ? null : new ParentTagLockKey(tags);
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
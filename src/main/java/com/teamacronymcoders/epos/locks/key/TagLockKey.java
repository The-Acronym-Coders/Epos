package com.teamacronymcoders.epos.locks.key;

import com.teamacronymcoders.epos.api.locks.key.ILockKey;
import com.teamacronymcoders.epos.api.locks.key.NBTLockKey;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

//TODO: Do we want a param that says "what type" the tag is for (would it even make sense to have one like that)
public class TagLockKey extends NBTLockKey {

    private static final List<TagLockKey> EMPTY = Collections.emptyList();

    private final ResourceLocation tag;

    public TagLockKey(ResourceLocation tag) {
        this(tag, null);
    }

    public TagLockKey(@Nonnull ResourceLocation tag, @Nullable CompoundNBT nbt) {
        super(nbt);
        this.tag = tag;
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isNotFuzzy() ? this : new TagLockKey(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof TagLockKey && tag.equals(((TagLockKey) o).tag)) {
            return getNBT() == null ? ((TagLockKey) o).getNBT() == null : getNBT().equals(((TagLockKey) o).getNBT());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, nbt);
    }

    @Nonnull
    public static List<TagLockKey> getKeysFromObject(@Nonnull Object object) {
        //TODO: Make an easier way for third party mods to add an object -> tag list/tags system
        // At that point it may make more sense for TagLockKey to just directly keep track of Tag<TYPE>
        if (object instanceof ItemStack) {
            ItemStack stack = (ItemStack) object;
            if (stack.isEmpty()) {
                return EMPTY;
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
        }//TODO: Check forge's custom tag types
        return EMPTY;
    }

    @Nonnull
    public static List<TagLockKey> fromTags(@Nonnull Collection<ResourceLocation> tags) {
        return fromTags(tags, null);
    }

    @Nonnull
    public static List<TagLockKey> fromTags(@Nonnull Collection<ResourceLocation> tags, @Nullable CompoundNBT nbt) {
        if (tags.isEmpty()) {
            return EMPTY;
        }
        return tags.stream().map(tag -> new TagLockKey(tag, nbt)).collect(Collectors.toList());
    }
}
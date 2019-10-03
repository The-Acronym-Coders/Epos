package com.teamacronymcoders.epos.locks.keys;

import com.teamacronymcoders.epos.api.locks.keys.IFuzzyLockKey;
import com.teamacronymcoders.epos.api.locks.keys.ILockKey;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;

public class BlockStateLockKey implements IFuzzyLockKey {

    private static Map<IProperty<?>, Comparable<?>> EMPTY_PROPERTIES = Collections.emptyMap();

    @Nonnull
    private final Block block;
    //Can be empty if we only care about the block type and not its state
    @Nonnull
    private final Map<IProperty<?>, Comparable<?>> properties;

    public BlockStateLockKey(@Nonnull BlockState state) {
        this(state.getBlock(), state.getValues());
    }

    public BlockStateLockKey(@Nonnull Block block) {
        this(block, EMPTY_PROPERTIES);
    }

    private BlockStateLockKey(@Nonnull Block block, @Nonnull Map<IProperty<?>, Comparable<?>> properties) {
        this.block = block;
        this.properties = properties;
    }

    @Nullable
    public static BlockStateLockKey fromObject(@Nonnull Object object) {
        if (object instanceof BlockState) {
            return new BlockStateLockKey((BlockState) object);
        } else if (object instanceof Block) {
            return new BlockStateLockKey(((Block) object));
        }
        return null;
    }

    @Override
    public boolean fuzzyEquals(@Nonnull IFuzzyLockKey o) {
        if (o == this) {
            return true;
        }
        if (o instanceof BlockStateLockKey) {
            BlockStateLockKey other = (BlockStateLockKey) o;
            if (block == other.block) {
                //Compare the properties making sure that they match
                return properties.entrySet().stream().allMatch(entry -> compareProperty(entry.getKey(), other));
            }
        }
        return false;
    }

    private <T extends Comparable<T>> boolean compareProperty(IProperty<T> property, BlockStateLockKey other) {
        if (other.properties.containsKey(property)) {
            //TODO: If we find a use case where it makes sense to use the compareTo as >= 0
            // so that it is fuzzy in that regard, potentially change this to >= from ==
            return getValue(property).compareTo(other.getValue(property)) == 0;
        }
        return false;
    }

    private <T extends Comparable<T>> T getValue(IProperty<T> property) {
        return (T) properties.get(property);
    }

    @Override
    public boolean isNotFuzzy() {
        return properties.isEmpty();
    }

    @Override
    @Nonnull
    public ILockKey getNotFuzzy() {
        return isNotFuzzy() ? this : new BlockStateLockKey(block);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BlockStateLockKey)) {
            return false;
        }
        BlockStateLockKey other = (BlockStateLockKey) o;
        //Note: Map#equals is declared to ensure they are both maps and that the keys and values are equal.
        // The type of the map does not matter
        return block == other.block && properties.equals(other.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block, properties);
    }
}